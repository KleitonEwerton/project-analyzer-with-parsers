
package com.mycompany.testerefactoringminer2.v;

import com.mycompany.testerefactoringminer2.Commit;
import com.mycompany.testerefactoringminer2.v.CLI.CLIExecute;
import com.mycompany.testerefactoringminer2.v.CLI.CLIExecution;

import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.io.IOException;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

public class ExecRefactoringMiner240v {

    public static String dataPasta = "data/";
    public static String logFile = "data/log.json";
    public static long exeMaxTime = 3600000;
    public static Map<String, String> mapHashEmail = new HashMap<>();

    public static void main(String[] args) throws Exception {

        String nomeProjeto = "java-paser-refactoring-and-comments";
        String url = "https://github.com/KleitonEwerton/java-paser-refactoring-and-comments.git";

        final Thread[] thread = new Thread[1];

        final boolean[] isTimedOut = { false };

        thread[0] = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            try {

                checar(nomeProjeto, url);

            } catch (Exception e) {

                e.printStackTrace();

            }
            long endTime = System.currentTimeMillis();

            long executionTime = endTime - startTime;

            if (executionTime > exeMaxTime) { // timeout

                isTimedOut[0] = true;

            }

        });

        thread[0].start();

        try {
            thread[0].join(exeMaxTime);
            if (isTimedOut[0]) {

                thread[0].interrupt(); // Interrompe a thread se exceder o tempo
            }
        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    }

    public static void checar(String projectName, String projectUrl) throws Exception {

        ExecRefactoringMiner240v.mapHashEmail.clear();
        CommentReporterComplete.todosOsComentarios.clear();
        CommentReporterComplete.mapHashQntComentarios.clear();
        CommentReporterComplete.mapHashQntSegmentos.clear();
        RefactoringSave.refactoringList.clear();
        RefactoringSave.mapHashQntRefatoracoes.clear();
        Usuario.usuariosList.clear();
        SalvarDados.salvarDadosList.clear();
        Commit.commits.clear();

        GitService gitService = new GitServiceImpl();

        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        Repository repo = gitService.cloneIfNotExists("tmp/" + projectName, projectUrl);

        getCommits("tmp/" + projectName);

        System.out.println("Iniciando...");

        System.out.println("Projeto: " + projectUrl);

        String command = "git log --all --pretty=format:\"%H|%ae|%ai\"";

        CLIExecution execute = CLIExecute.execute(command, "tmp/" + projectName);

        for (String line : execute.getOutput()) {

            String[] parts = line.split("\\|");
            Commit commit = Commit.getCommitByHash(parts[0]).get();
            // ! É o unico pai por enquanto, quando mudar a abordagem devera mudar
            Usuario.usuariosList
                    .add(new Usuario(commit.getHash(), commit.getParentHash(), parts[1], parts[2]));
            ExecRefactoringMiner240v.mapHashEmail.put(parts[0], parts[1]);

        }

        try {

            for (Commit commit : Commit.commits) {

                try {
                    miner.detectAtCommit(repo, commit.getHash(), new RefactoringHandler() {

                        @Override
                        public void handle(String commitHash, List<Refactoring> refactorings) {

                            // System.out.println(commitHash + "==" + commit.getHash());

                            for (Refactoring ref : refactorings) {

                                String refactoringType = ref.getRefactoringType().toString();

                                String referencia = ref.getName() + " "
                                        + ref.toString().replace(",", " ").replace(";", " ");

                                new RefactoringSave(commitHash, commit.getParentHash(),
                                        refactoringType, referencia);

                            }

                        }

                    });

                    CommentReporterComplete.walkToRepositorySeachComment("tmp/" + projectName, commit.getHash(),
                            commit.getParentHash());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        for (Commit commit : Commit.commits) {
            salvarOsDados(commit);
        }

        System.out.println("Salvando dados...");

        CommentReporterComplete.saveCommentsCSV("csv/comments-" + projectName + ".csv");
        RefactoringSave.saveRefactoringCSV("csv/refactorings-" + projectName + ".csv");
        Usuario.saveUserCommitsCSV("csv/commits-" + projectName + ".csv");
        SalvarDados.saveDadosCSV("csv/dados-" + projectName + ".csv");

        System.out.println("Finalizado!");
    }

    public static void salvarOsDados(Commit commit) {
        String hash = commit.getHash();
        String parentHash = commit.getParentHash();

        Integer qntRefatoracoes = Optional.ofNullable(RefactoringSave.mapHashQntRefatoracoes.get(hash)).orElse(0);
        Integer qntComentarios = Optional.ofNullable(CommentReporterComplete.mapHashQntComentarios.get(hash)).orElse(0);
        Integer qntSegmentos = Optional.ofNullable(CommentReporterComplete.mapHashQntSegmentos.get(hash)).orElse(0);

        Integer qntRefatoracoesParent = Optional.ofNullable(RefactoringSave.mapHashQntRefatoracoes.get(parentHash))
                .orElse(0);
        Integer qntComentariosParent = Optional
                .ofNullable(CommentReporterComplete.mapHashQntComentarios.get(parentHash)).orElse(0);
        Integer qntSegmentosParent = Optional.ofNullable(CommentReporterComplete.mapHashQntSegmentos.get(parentHash))
                .orElse(0);

        new SalvarDados(
                hash,
                qntRefatoracoes,
                qntComentarios,
                qntSegmentos,
                parentHash,
                qntRefatoracoesParent,
                qntComentariosParent,
                qntSegmentosParent);
    }

    public static void getCommits(String path) throws IOException {

        String command = "git log --all --pretty=\"format:'%H''%P'\"";

        CLIExecution execute = CLIExecute.execute(command, path);

        if (!execute.getError().isEmpty()) {
            throw new RuntimeException("The path does not have a Git Repository or Name is Bigger");
        }

        for (String line : execute.getOutput()) {

            int hashBegin = line.indexOf("\'");
            int hashEnd = line.indexOf("\'", hashBegin + 1);
            int parentsBegin = line.indexOf("\'", hashEnd + 1);
            int parentsEnd = line.indexOf("\'", parentsBegin + 1);

            String hash = line.substring(hashBegin + 1, hashEnd);
            String parents = line.substring(parentsBegin + 1, parentsEnd);

            String parenstsArray[] = parents.split(" ");
            Set<String> parentsSet = new HashSet<>();

            for (String parent : parenstsArray) {
                parentsSet.add(parent);
            }

            // ! ESTAMOS ANALISANDO APENAS COMMITS COM 1 PAI
            if (parentsSet.size() == 1) {
                new Commit(hash, parentsSet);
            }
        }
    }

}
