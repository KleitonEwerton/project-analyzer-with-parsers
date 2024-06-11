
package com.mycompany.testerefactoringminer2.v;

import com.mycompany.testerefactoringminer2.Commit;
import com.mycompany.testerefactoringminer2.v.CLI.CLIExecute;
import com.mycompany.testerefactoringminer2.v.CLI.CLIExecution;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

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
    public static long exeMaxTime = 360000000;
    public static Map<String, String> mapHashEmail = new HashMap<>();

    public static void main(String[] args) throws Exception {

        String nomeProjeto = "examples-for-refactoring-testing";
        String url = "https://github.com/KleitonEwerton/examples-for-refactoring-testing.git";

        // String nomeProjeto = "auto";
        // String url = "https://github.com/google/auto.git";

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

                thread[0].interrupt();
            }
        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    }

    public static void checar(String projectName, String projectUrl) throws Exception {

        ExecRefactoringMiner240v.mapHashEmail.clear();
        CommentReporterComplete.todosOsComentarios.clear();
        CommentReporterComplete.todosHashErro.clear();
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
            try {
                Commit commit = Commit.getCommitByHash(parts[0]).get();
                Usuario.usuariosList
                        .add(new Usuario(commit.getHash(), commit.getParentHash(), parts[1], parts[2]));
                ExecRefactoringMiner240v.mapHashEmail.put(parts[0], parts[1]);

            } catch (Exception e) {

            }

        }

        try {

            System.out.println("Analisando todos os arquivos em cada versão do projeto!");
            getAndSaveAllFiles(projectName);

            System.out.println("Analisando todos os comentarios em cada versão do projeto!");
            getAndSaveAllComments(projectName);

            System.out.println("Analisando todos as refatorações em cada versão do projeto!");
            for (Commit commit : Commit.commits) {

                try {
                    miner.detectAtCommit(repo, commit.getHash(), new RefactoringHandler() {

                        @Override
                        public void handle(String commitHash, List<Refactoring> refactorings) {
                            salveRefactiongByRefactionList(refactorings, commit);
                        }

                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        chamadaDoSalvaDados(projectName);
    }

    public static void salveRefactiongByRefactionList(List<Refactoring> refactorings, Commit commit) {
        for (Refactoring ref : refactorings) {

            String refactoringType = ref.getRefactoringType().toString();

            String referencia = ref.getName() + " "
                    + ref.toString().replace(",", " ").replace(";", " ");

            new RefactoringSave(commit.getHash(), commit.getParentHash(),
                    refactoringType, referencia);

        }
    }

    public static void getAndSaveAllComments(String projectName) {
        for (Commit commit : Commit.commits) {

            try {

                CommentReporterComplete.atualHash = commit.getHash();
                CommentReporterComplete.parentHash = commit.getParentHash();

                String command = "git checkout " + commit.getHash();
                CLIExecute.execute(command, "tmp/" + projectName);

                System.out.println("check comments - tmp/" + projectName + "/" + commit.getHash());
                CommentReporterComplete.walkToRepositorySeachComment(commit.getFilesPath());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void getAndSaveAllFiles(String projectName) {
        for (Commit commit : Commit.commits) {

            try {

                String command = "git checkout " + commit.getHash();
                CLIExecute.execute(command, "tmp/" + projectName);

                System.out.println("check files - tmp/" + projectName + "/" + commit.getHash());
                commit.setFilesPath(CommentReporterComplete.collectJavaFiles("tmp/" + projectName, commit));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void chamadaDoSalvaDados(String projectName)
            throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
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

        // ! Não salva quem tem mais de 1 pai
        if (commit.getSizeParents() != 1 || commit.getHash().equals("") || commit.getParentHash().equals(""))
            return;

        System.out.println(commit.getParent().getFilesPath().size() + " - " + commit.getParent().getHash());
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

            // ! ESTAMOS ANALISANDO APENAS COMMITS COM 1 PAI, AQUI EXISTE OUTRAS VARIACOES
            new Commit(hash, parentsSet);

        }

        for (Commit commit : Commit.commits) {
            Commit parent = Commit.getCommitByHash(commit.getParentHash()).orElse(null);
            if (parent != null)
                commit.setParent(parent);
        }

    }

}
