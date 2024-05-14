
package com.mycompany.testerefactoringminer2.v;

import com.mycompany.testerefactoringminer2.Commit;
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
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
        RefactoringSave.refactoringList.clear();
        Usuario.usuariosList.clear();
        SalvarDados.salvarDadosList.clear();
        Commit.commits.clear();

        GitService gitService = new GitServiceImpl();

        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        Repository repo = gitService.cloneIfNotExists("tmp/" + projectName, projectUrl);

        Set<Commit> commits = printCommits("tmp/" + projectName);

        System.out.println("Iniciando...");

        System.out.println("Projeto: " + projectUrl);

        String command = "git log --all --pretty=format:\"%H|%ae|%ai\"";

        CLIExecution execute = CLIExecute.execute(command, "tmp/" + projectName);

        for (String line : execute.getOutput()) {

            String[] parts = line.split("\\|");
            // ! É o unico pai por enquanto, quando mudar a abordagem devera mudar
            Usuario.usuariosList
                    .add(new Usuario(parts[0], Commit.getFirstParentByHash(parts[0]).get(), parts[1], parts[2]));
            ExecRefactoringMiner240v.mapHashEmail.put(parts[0], parts[1]);

        }

        try {

            for (Commit commit : commits) {

                CommentReporterComplete.qntComentarios = 0;
                CommentReporterComplete.qntSegmentos = 0;
                RefactoringSave.qntRefatoracoes = 0;

                try {
                    miner.detectAtCommit(repo, commit.getHash(), new RefactoringHandler() {
                        @Override
                        public void handle(String commitId, List<Refactoring> refactorings) {

                            System.out.println(commitId + "==" + commit.getHash());

                            for (Refactoring ref : refactorings) {

                                String refactoringType = ref.getRefactoringType().toString();

                                String referencia = ref.getName() + " "
                                        + ref.toString().replace(",", " ").replace(";", " ");

                                RefactoringSave.refactoringList
                                        .add(new RefactoringSave(commit.getHash(), commit.getParentsHash(),
                                                refactoringType, referencia));

                            }

                        }

                    });

                    CommentReporterComplete.walkToRepositorySeachComment("tmp/" + projectName, commit.getHash(),
                            commit.getParentsHash());

                    SalvarDados.salvarDadosList.add(new SalvarDados(commit.getHash(), RefactoringSave.qntRefatoracoes,
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

    public static Set<Commit> printCommits(String path) throws IOException {

        String command = "git log --all --pretty=\"format:'%H''%P'\"";
        Pattern COMMIT_PATTERN = Pattern.compile(".*'(\\w+)'\\s*'.*?'\\s*'(.*?)'");

        CLIExecution execute = CLIExecute.execute(command, path);

        if (!execute.getError().isEmpty()) {
            throw new RuntimeException("The path does not have a Git Repository or Name is Bigger");
        }

        for (String line : execute.getOutput()) {
            Matcher matcher = COMMIT_PATTERN.matcher(line);
            if (matcher.matches()) {
                String hash = matcher.group(1);
                String parentsString = matcher.group(2);
                String[] parents = parentsString.split(" ");

                // ! parents.length == 1
                if (parents.length == 1) {
                    Set<String> parentsSet = new HashSet<>();
                    for (String parent : parents) {
                        parentsSet.add(parent);
                    }
                    Commit.commits.add(new Commit(hash, parentsSet));
                }
            }
        }

        return Commit.commits;

    }

}
