
package com.minerprojects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import com.minerprojects.CLI.CLIExecute;
import com.minerprojects.CLI.CLIExecution;
import com.minerprojects.badsmelldetector.ExecutionConfig;

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

        Repository repo = gitService.cloneIfNotExists("tmp/" + projectName, projectUrl);

        logger.info("Iniciando...");

        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info(String.format("Projeto: %s", projectUrl));
        }

        ExecutionConfig.PROJECT_PATH = "C:\\Users\\kleit\\OneDrive\\Documentos\\tcc\\java-paser-refactoring-and-comments\\projectextractor\\tmp\\"
                + projectName;

        logger.info("GET COMMITS AND MODIFIED, ADDED AND DELETED FILES: " + ExecutionConfig.PROJECT_PATH);

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

        String command = "git log --all --pretty=\"hash:'%H'parents:'%P'\" --name-status --reverse";
        String path = "tmp/" + projectName;

        CLIExecution execute = CLIExecute.execute(command, path);

        if (!execute.getError().isEmpty()) {
            throw new RuntimeException("The path does not have a Git Repository or Name is Bigger");
        }

        List<CommitReporter> commitsList = new ArrayList<>();
        CommitReporter currentCommit = null;

        for (String line : execute.getOutput()) {
            if (line.startsWith("hash:")) {
                currentCommit = processCommitLine(projectName, line, commitsList);
            } else if (currentCommit != null
                    && (line.startsWith("M") || line.startsWith("A") || line.startsWith("D"))) {
                processFileLine(line, currentCommit);
            }
        }

        configureParentChildRelationships(commitsList);

        CommitReporter.commits = commitsList;
    }

    private static CommitReporter processCommitLine(String projectName, String line,
            List<CommitReporter> commitsList) {
        int hashBegin = line.indexOf("'") + 1;
        int hashEnd = line.indexOf("'", hashBegin);
        int parentsBegin = line.indexOf("'", hashEnd + 1) + 1;
        int parentsEnd = line.indexOf("'", parentsBegin);

        String hash = line.substring(hashBegin, hashEnd);
        String parents = line.substring(parentsBegin, parentsEnd);

        Set<String> parentsSet = new HashSet<>(Arrays.asList(parents.split(" ")));
        CommitReporter currentCommit = new CommitReporter(projectName, hash, parentsSet, new HashMap<>());
        commitsList.add(currentCommit); // Adiciona o commit à lista em ordem
        return currentCommit;
    }

    private static void processFileLine(String line, CommitReporter currentCommit) {
        String[] parts = line.split("\t", 2);
        if (parts.length == 2) {
            String status = parts[0].trim();
            String filePath = parts[1].trim();
            filePath = filePath.replace("/", File.separator);
            if (filePath.endsWith(".java")) {
                currentCommit.getFilesMAD().put(filePath, status);
                currentCommit.getJavaFiles().add(filePath);
            }

        }

    }

    private static void configureParentChildRelationships(List<CommitReporter> commitsList) {
        Map<String, CommitReporter> commitsMap = commitsList.stream()
                .collect(Collectors.toMap(CommitReporter::getHash, commit -> commit));

        for (CommitReporter commit : commitsList) {
            for (String parentHash : commit.getParentHashes()) {

                CommitReporter parent = commitsMap.get(parentHash);
                if (parent != null) {
                    commit.setParent(parent);
                }
            }
        }
    }

}
