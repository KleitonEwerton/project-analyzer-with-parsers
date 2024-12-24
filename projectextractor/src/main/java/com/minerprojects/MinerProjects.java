
package com.minerprojects;

import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;
import com.minerprojects.CLI.CLIExecute;
import com.minerprojects.CLI.CLIExecution;
import com.minerprojects.badsmelldetector.ExecutionConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.logging.Logger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MinerProjects {

    private static final Logger logger = Logger.getLogger(MinerProjects.class.getName());

    public static void main(String[] args) throws Exception {

        String nomeProjeto = "auto";
        String url = "https://github.com/google/auto.git";

        checar(nomeProjeto, url);

    }

    public static void checar(String projectName, String projectUrl) throws Exception {

        CommitReporter.commits.clear();

        GitService gitService = new GitServiceImpl();

        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        gitService.cloneIfNotExists("tmp/" + projectName, projectUrl);

        logger.info("Iniciando...");

        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info(String.format("Projeto: %s", projectUrl));
        }

        ExecutionConfig.PROJECT_PATH = "C:\\Users\\kleit\\OneDrive\\Documentos\\tcc\\java-paser-refactoring-and-comments\\projectextractor\\tmp\\"
                + projectName;

        System.out.println("GET COMMITS AND MODIFIED, ADDED AND DELETED FILES: " + ExecutionConfig.PROJECT_PATH);

        getCommits(projectName);

        try {

            logger.info("Analisando todos os comentarios em cada versão do projeto!");
            // CommentReporter.getAllComments(projectName);

            logger.info("Analisando todos as refatorações em cada versão do projeto!");
            // RefactoringReporter.getAllRefactoring(miner, repo);

            logger.info("Analisando todos os PMD em cada versão do projeto!");
            PMDReporter.getAllPMD(projectName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Finalizado!");

    }

    public static void getCommits(String projectName) throws IOException {
        String command = "git log --all --pretty=\"hash:'%H'parents:'%P'\" --name-status";
        String path = "tmp/" + projectName;

        CLIExecution execute = CLIExecute.execute(command, path);

        if (!execute.getError().isEmpty()) {
            throw new RuntimeException("The path does not have a Git Repository or Name is Bigger");
        }

        Map<String, CommitReporter> commitsMap = new HashMap<>();
        CommitReporter currentCommit = null;

        for (String line : execute.getOutput()) {
            if (line.startsWith("hash:")) {
                // Processa uma nova entrada de commit
                int hashBegin = line.indexOf("'") + 1;
                int hashEnd = line.indexOf("'", hashBegin);
                int parentsBegin = line.indexOf("'", hashEnd + 1) + 1;
                int parentsEnd = line.indexOf("'", parentsBegin);

                String hash = line.substring(hashBegin, hashEnd);
                String parents = line.substring(parentsBegin, parentsEnd);

                Set<String> parentsSet = new HashSet<>(Arrays.asList(parents.split(" ")));
                currentCommit = new CommitReporter(projectName, hash, parentsSet, new HashMap<>());
                commitsMap.put(hash, currentCommit);

            } else if (currentCommit != null
                    && (line.startsWith("M") || line.startsWith("A") || line.startsWith("D"))) {
                // Processa arquivos modificados, adicionados ou deletados
                String[] parts = line.split("\t", 2);
                if (parts.length == 2) {
                    String status = parts[0].trim();
                    String filePath = parts[1].trim();
                    currentCommit.getFilesMAD().put(filePath, status);
                }
            }
        }

        // Configura as relações pai-filho entre os commits
        for (CommitReporter commit : commitsMap.values()) {
            for (String parentHash : commit.getParentHashes()) {
                CommitReporter parent = commitsMap.get(parentHash);
                if (parent != null) {
                    commit.setParent(parent);
                }
            }
        }

        // Exibe os commits para depuração
        for (CommitReporter commit : commitsMap.values()) {
            System.out.println("Commit: " + commit.getHash() + " " + commit.getFilesMAD().size());
        }
    }

}
