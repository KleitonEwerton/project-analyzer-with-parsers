
package com.mycompany.testerefactoringminer2.v;

import com.mycompany.testerefactoringminer2.v.CLI.CLIExecute;
import com.mycompany.testerefactoringminer2.v.CLI.CLIExecution;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import org.eclipse.jgit.lib.Repository;

import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

public class ExecRefactoringMiner240v {

    public static String dataPasta = "data/";
    public static String logFile = "data/log.json";
    public static long exeMaxTime = 3600000;
    public static Map<String, String> mapHashEmail = new HashMap<>();

    public static void main(String[] args) throws Exception {

        //String nomeProjeto = "java-paser-refactoring-and-comments";
        //String url = "https://github.com/KleitonEwerton/java-paser-refactoring-and-comments.git";

        String nomeProjeto = "auto";
        String url = "https://github.com/google/auto.git";

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

        GitService gitService = new GitServiceImpl();

        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        Repository repo = gitService.cloneIfNotExists("tmp/" + projectName, projectUrl);

        List<String> commits = printCommits("tmp/" + projectName);

        ExecRefactoringMiner240v.mapHashEmail.clear();
        CommentReporterComplete.todosOsComentarios.clear();
        RefactoringSave.refactoringList.clear();
        Usuario.usuariosList.clear();
        SalvarDados.salvarDadosList.clear();

        System.out.println("Iniciando...");

        System.out.println("Projeto: " + projectUrl);

        String command = "git log --all --pretty=format:\"%H|%ae|%ai\"";

        CLIExecution execute = CLIExecute.execute(command, "tmp/" + projectName);

        for (String line : execute.getOutput()) {

            String[] parts = line.split("\\|");
            Usuario.usuariosList.add(new Usuario(parts[0], parts[1], parts[2]));
            ExecRefactoringMiner240v.mapHashEmail.put(parts[0], parts[1]);

        }

        try {

            for (String hashCommit : commits) {

                CommentReporterComplete.qntComentarios = 0;
                CommentReporterComplete.qntSegmentos = 0;
                RefactoringSave.qntRefatoracoes = 0;

                try {
                    miner.detectAtCommit(repo, hashCommit, new RefactoringHandler() {
                        @Override
                        public void handle(String commitId, List<Refactoring> refactorings) {

                            for (Refactoring ref : refactorings) {

                                String refactoringType = ref.getRefactoringType().toString();
                                String referencia = ref.getName() + " "
                                        + ref.toString().replace(",", " ").replace(";", " ");
                                RefactoringSave.refactoringList
                                        .add(new RefactoringSave(hashCommit, refactoringType, referencia));

                            }

                        }

                    });

                    CommentReporterComplete.walkToRepositorySeachComment("tmp/" + projectName, hashCommit);

                    SalvarDados.salvarDadosList.add(new SalvarDados(hashCommit, RefactoringSave.qntRefatoracoes,
                            CommentReporterComplete.qntComentarios, CommentReporterComplete.qntSegmentos));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println("Salvando dados...");

        saveCommentsCSV("csv/comments-" + projectName + ".csv");
        saveRefactoringCSV("csv/refactorings-" + projectName + ".csv");
        saveUserCommitsCSV("csv/commits-" + projectName + ".csv");
        saveDadosCSV("csv/dados-" + projectName + ".csv");

        System.out.println("Finalizado!");
    }

    public static void saveDadosCSV(String fileName)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        Writer writer = Files.newBufferedWriter(Paths.get(fileName));

        @SuppressWarnings({ "unchecked", "rawtypes" })
        StatefulBeanToCsv<SalvarDados> beanToCsv = new StatefulBeanToCsvBuilder(
                writer).build();

        beanToCsv.write(SalvarDados.salvarDadosList);
        writer.flush();
        writer.close();
        SalvarDados.salvarDadosList.clear();

    }

    public static void saveCommentsCSV(String fileName)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        Writer writer = Files.newBufferedWriter(Paths.get(fileName));

        @SuppressWarnings({ "unchecked", "rawtypes" })
        StatefulBeanToCsv<CommentReporterComplete.CommentReportEntry> beanToCsv = new StatefulBeanToCsvBuilder(
                writer).build();

        beanToCsv.write(CommentReporterComplete.todosOsComentarios);
        writer.flush();
        writer.close();
        CommentReporterComplete.todosOsComentarios.clear();

    }

    public static void saveRefactoringCSV(String fileName)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        Writer writer = Files.newBufferedWriter(Paths.get(fileName));

        @SuppressWarnings({ "unchecked", "rawtypes" })
        StatefulBeanToCsv<RefactoringSave> beanToCsv = new StatefulBeanToCsvBuilder(
                writer).build();

        beanToCsv.write(RefactoringSave.refactoringList);
        writer.flush();
        writer.close();
        RefactoringSave.refactoringList.clear();

    }

    public static void saveUserCommitsCSV(String fileName)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        Writer writer = Files.newBufferedWriter(Paths.get(fileName));

        @SuppressWarnings({ "unchecked", "rawtypes" })
        StatefulBeanToCsv<Usuario> beanToCsv = new StatefulBeanToCsvBuilder(
                writer).build();

        beanToCsv.write(Usuario.usuariosList);
        writer.flush();
        writer.close();
        Usuario.usuariosList.clear();

    }

    public static List<String> printCommits(String path) throws IOException {

        String command = "git log --all --pretty=\"format:'%H''%P'\"";

        CLIExecution execute = CLIExecute.execute(command, path);

        List<String> hashs = new ArrayList<>();

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
            List<String> parentsList = new ArrayList<>();
            for (String parent : parenstsArray) {
                parentsList.add(parent);
            }

            if (parentsList.size() == 1) {
                hashs.add(hash);

            }
        }

        return hashs;

    }

}
