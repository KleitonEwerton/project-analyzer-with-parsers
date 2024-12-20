
package com.minerprojects;

import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;
import com.minerprojects.CLI.CLIExecute;
import com.minerprojects.CLI.CLIExecution;
import com.minerprojects.badsmelldetector.ExecutionConfig;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;
import java.util.HashSet;

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

        String command = "git log --all --pretty=\"format:'%H''%P'\"";
        String path = "tmp/" + projectName;

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

            String[] parenstsArray = parents.split(" ");

            Set<String> parentsSet = new HashSet<>();

            for (String parent : parenstsArray) {
                parentsSet.add(parent);
            }

            new CommitReporter(projectName, hash, parentsSet);

        }

        for (CommitReporter commit : CommitReporter.commits) {
            CommitReporter parent = CommitReporter.getCommitByHash(commit.getParentHash()).orElse(null);

            if (parent != null)
                commit.setParent(parent);
        }

    }

}
