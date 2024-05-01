
package com.mycompany.testerefactoringminer2.v;

import com.mycompany.testerefactoringminer2.v.CLI.CLIExecute;
import com.mycompany.testerefactoringminer2.v.CLI.CLIExecution;

import org.eclipse.jgit.lib.Repository;

import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import java.util.*;

public class ExecRefactoringMiner240v {

    public static String dataPasta = "data/";
    public static String logFile = "data/log.json";
    public static long exeMaxTime = 3600000;

    public static void oldMain(String[] args) throws Exception {

        String caminhoDoArquivo = dataPasta + "java-teste.xlsx";

        Map<String, String[]> projetos = lerPlanilha(caminhoDoArquivo);

        for (Map.Entry<String, String[]> projeto : projetos.entrySet()) {
            String nomeProjeto = projeto.getKey();
            String[] atributos = projeto.getValue();

            String url = atributos[0];

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
                thread[0].join(exeMaxTime); // Aguarda a conclusão da tarefa ou o tempo de execução
                if (isTimedOut[0]) {

                    thread[0].interrupt(); // Interrompe a thread se exceder o tempo
                }
            } catch (InterruptedException e) {
            }

        }

    }

    public static void main(String[] args) throws Exception {

        String nomeProjeto = "java-paser-refactoring-and-comments";
        // String url = "https://github.com/google/auto.git";
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
            thread[0].join(exeMaxTime); // Aguarda a conclusão da tarefa ou o tempo de execução
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

        Map<String, List<String>> refactoringMap = new HashMap<>();

        List<String> commits = printCommits("tmp/" + projectName);

        System.out.println("Iniciando...");

        System.out.println("Projeto: " + projectUrl);

        String command = "git log --all --pretty=format:\"%H|%ae|%ai\"";

        CLIExecution execute = CLIExecute.execute(command, "tmp/" + projectName);

        HashMap<String, String[]> dadosDosCommits = new HashMap<>();

        for (String line : execute.getOutput()) {

            String[] parts = line.split("\\|");

            dadosDosCommits.put(parts[0], new String[] { parts[1], parts[2] });
            System.out.println(parts[0] + " " + parts[1] + " " + parts[2]);
        }

        try {

            for (String hashCommit : commits) {

                List<String> refactoringList = new ArrayList<>();

                try {

                    miner.detectAtCommit(repo, hashCommit, new RefactoringHandler() {
                        @Override
                        public void handle(String commitId, List<Refactoring> refactorings) {

                            for (Refactoring ref : refactorings) {

                                String refactoringType = ref.getRefactoringType().toString();
                                String referencia = ref.getName() + " " + ref.toString();
                                referencia = referencia.replace(",", " ");
                                referencia = referencia.replace(";", " ");
                                refactoringList.add(refactoringType + ", " + referencia);
                            }

                            refactoringMap.put(commitId, refactoringList);

                        }

                    });

                    CommentReporterComplete.walkToRepositorySeachComment("tmp/" + projectName, hashCommit);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        // salvarDadosParaCSV(salvarDados, "dados-teste.csv");

        System.out.println("Salvando dados...");

        salvarPlanilha(refactoringMap, projectName);

        System.out.println("Finalizado!");
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

    public static void salvarPlanilha(Map<String, List<String>> refactoringMap, String projectName) {
        try (PrintWriter writer = new PrintWriter(new File(dataPasta + projectName + "-ref-miner.csv"))) {

            StringBuilder sb = new StringBuilder();
            sb.append("Hash");
            sb.append(',');
            sb.append("Refatoração");
            sb.append(',');
            sb.append("Descrição");

            sb.append('\n');

            for (Map.Entry<String, List<String>> entry : refactoringMap.entrySet()) {
                String hash = entry.getKey();
                List<String> refactoringList = entry.getValue();

                for (String ref : refactoringList) {
                    String refatocao[] = ref.split(",");
                    sb.append(hash);
                    sb.append(',');
                    sb.append(refatocao[0]);
                    sb.append(',');
                    sb.append(refatocao[1]);
                    sb.append('\n');

                }
            }

            writer.write(sb.toString());
            System.out.println("done!");

        } catch (FileNotFoundException e) {

        }
    }

    public static Map<String, String[]> lerPlanilha(String caminhoDoArquivo) {
        Map<String, String[]> projetos = new HashMap<>();

        try (FileInputStream arquivo = new FileInputStream(caminhoDoArquivo)) {
            Workbook workbook = new XSSFWorkbook(arquivo);
            Sheet sheet = workbook.getSheetAt(0); // Assumindo que a planilha que você deseja ler está na primeira aba
                                                  // (índice 0)
            System.out.println("Projetos Listados:");
            for (Row row : sheet) {
                String nomeProjeto = row.getCell(0).getStringCellValue();
                String url = row.getCell(1).getStringCellValue();
                String[] atributos = { url };
                projetos.put(nomeProjeto, atributos);
                System.out.println(" - " + nomeProjeto + " URL:" + url);
            }

            arquivo.close();
        } catch (IOException e) {
        }
        System.out.println("\n");
        return projetos;
    }

    public static <K, V> int contarCommitsAnalisados(Map<K, V> map) {
        Set<K> chavesUnicas = new HashSet<>();

        for (K chave : map.keySet()) {
            chavesUnicas.add(chave);
        }

        return chavesUnicas.size();
    }
}
