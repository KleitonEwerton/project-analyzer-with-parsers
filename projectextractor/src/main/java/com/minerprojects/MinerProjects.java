
package com.minerprojects;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import com.minerprojects.cli.CLIExecute;
import com.minerprojects.cli.CLIExecution;
import com.minerprojects.pmddetector.ExecutionConfig;

public class MinerProjects {

    private static final Logger logger = Logger.getLogger(MinerProjects.class.getName());

    public static void main(String[] args) throws Exception {

        String nomeProjeto = "project-analyzer-with-parsers";
        String url = "https://github.com/KleitonEwerton/project-analyzer-with-parsers.git";

        // String nomeProjeto = "openpnp";
        // String url = "https://github.com/openpnp/openpnp.git";

        // String nomeProjeto = "morphia";
        // String url = "https://github.com/MorphiaOrg/morphia.git";

        // String nomeProjeto = "spring-data-mongodb";
        // String url = "https://github.com/spring-projects/spring-data-mongodb.git";

        // String nomeProjeto = "controlsfx";
        // String url = "https://github.com/controlsfx/controlsfx.git";

        // String nomeProjeto = "pgjdbc";
        // String url = "https://github.com/pgjdbc/pgjdbc.git";

        // String nomeProjeto = "httpcomponents-client";
        // String url = "https://github.com/apache/httpcomponents-client.git";

        // String nomeProjeto = "github-api";
        // String url = "https://github.com/hub4j/github-api.git";

        // String nomeProjeto = "mondrian";
        // String url = "https://github.com/pentaho/mondrian.git";

        // String nomeProjeto = "SpongeAPI";
        // String url = "https://github.com/SpongePowered/SpongeAPI.git";

        // String nomeProjeto = "SpongeForge";
        // String url = "https://github.com/SpongePowered/SpongeForge.git";

        checar(nomeProjeto, url);

    }

    public static void checar(String projectName, String projectUrl) throws Exception {

        CommitReporter.commits.clear();
        ErrorReporter.errosCheckout.clear();

        GitService gitService = new GitServiceImpl();

        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        Repository repo = gitService.cloneIfNotExists("tmp/" + projectName, projectUrl);

        logger.info("Iniciando...");

        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info(String.format("Projeto: %s", projectUrl));
        }

        ExecutionConfig.PROJECT_PATH = "C:\\Users\\kleit\\OneDrive\\Documentos\\tcc\\project-analyzer-with-parsers\\projectextractor\\tmp\\"
                + projectName;

        logger.info("Pegando todos os commit, arquivos modificados por commit e suas integridades no SO usado: "
                + ExecutionConfig.PROJECT_PATH);

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

    public static void getCommits(String projectName) throws IOException, InterruptedException {

        String command = "git log --all --pretty=\"hash:'%H'parents:'%P'\" --name-status --reverse";
        String path = "tmp/" + projectName;

        CLIExecution execute = CLIExecute.execute(command, path);

        if (!execute.getError().isEmpty()) {
            throw new RuntimeException("The path does not have a Git Repository or Name is Bigger");
        }

        Map<String, CommitReporter> commitsMap = new HashMap<>();
        CommitReporter currentCommit = null;

        int erros = 0;

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

                if (checarCheckout(currentCommit)) {
                    commitsMap.put(hash, currentCommit);
                } else {
                    erros++;
                }

            } else if (currentCommit != null
                    && (line.startsWith("M") || line.startsWith("A")) && line.endsWith(".java")) {

                String[] parts = line.split("\t", 2);
                if (parts.length == 2) {
                    String status = parts[0].trim();
                    String filePath = parts[1].trim();
                    currentCommit.getFilesMAD().put(filePath, status);
                    currentCommit.getJavaFiles().add(filePath);
                }
            }
        }

        logger.info("Erros: " + erros);
        logger.info("Checkouts sem erros: " + CommitReporter.commits.size());

        // Configura as relações pai-filho entre os commits
        for (CommitReporter commit : commitsMap.values()) {
            for (String parentHash : commit.getParentHashes()) {

                CommitReporter parent = commitsMap.get(parentHash);

                if (parent != null) {

                    commit.setParent(parent);

                }
            }
        }

    }

    public static boolean checarCheckout(CommitReporter commit) throws IOException, InterruptedException {

        String command = "git checkout " + commit.getHash();

        CLIExecution execute = CLIExecute.executeCheckout(command, "tmp" + File.separator + commit.getProjectName());

        if (execute.toString().contains("error:")) {

            if (logger.isLoggable(java.util.logging.Level.INFO)) {
                logger.info(String.format("ERROR%n%s%n%s", command, execute.toString()));
            }

            new ErrorReporter(commit.getHash(), commit.getProjectName(), command + "\n" + execute.toString());

            return false;

        } else {

            return true;

        }

    }

}
