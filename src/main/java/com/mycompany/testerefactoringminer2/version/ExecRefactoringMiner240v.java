
package com.mycompany.testerefactoringminer2.version;

import com.mycompany.testerefactoringminer2.version.CLI.CLIExecute;
import com.mycompany.testerefactoringminer2.version.CLI.CLIExecution;
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

        // String nomeProjeto = "flink-cdc";
        // String url = "https://github.com/apache/flink-cdc.git";

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
        CommentReporterComplete.qntSegmentosApenasArquivosExistenteNoPai.clear();
        RefactoringSave.refactoringList.clear();
        RefactoringSave.qntRefatoracoes.clear();
        Commit.commits.clear();

        GitService gitService = new GitServiceImpl();

        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        Repository repo = gitService.cloneIfNotExists("tmp/" + projectName, projectUrl);

        System.out.println("Iniciando...");

        System.out.println("Projeto: " + projectUrl);

        getCommits("tmp/" + projectName);

        try {

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

            String oldPathClass = ref.getInvolvedClassesBeforeRefactoring().toString().split(",")[0];
            String newPathClass = ref.getInvolvedClassesAfterRefactoring().toString().split(",")[0];

            new RefactoringSave(commit.getHash(), commit.getParentHash(),
                    refactoringType, referencia, oldPathClass.substring(2, oldPathClass.length()),
                    newPathClass.substring(2, newPathClass.length()));

        }
    }

    public static void getAndSaveAllComments(String projectName) {

        for (Commit commit : Commit.commits) {

            try {

                System.out.println("check comments - tmp/" + projectName + "/" + commit.getHash());
                String command = "git checkout " + commit.getHash();
                CLIExecute.executeCheckout(command, "tmp/" + projectName);
                CommentReporterComplete.walkToRepositorySeachComment(commit, projectName);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void chamadaDoSalvaDados(String projectName)
            throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {

        System.out.println("Salvando dados...");

        CommentReporterComplete.saveCommentsCSV("csv/comments-" + projectName + ".csv");
        RefactoringSave.saveRefactoringCSV("csv/refactorings-" + projectName + ".csv");

        System.out.println("Finalizado!");
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
