package com.minerprojects;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import com.minerprojects.badsmelldetector.ExecutionConfig;
import com.minerprojects.badsmelldetector.pmd.LongMethod;
import com.minerprojects.badsmelldetector.pmd.TooManyMethods;

public class PMDReporter {
    private static final Logger logger = Logger.getLogger(MinerProjects.class.getName());

    public static void getAllPMD(String projectName) {

        for (CommitReporter commit : CommitReporter.commits) {

            try {

                if (logger.isLoggable(java.util.logging.Level.INFO)) {
                    logger.info(String.format("check PMD - tmp/%s/%s", projectName, commit.getHash()));
                }

                PMDReporter.getPMD(commit, projectName);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void getPMD(CommitReporter commit, String projectName) throws Exception {

        String command = "git checkout " + commit.getHash();
        Path directory = Paths.get(ExecutionConfig.PROJECT_PATH);

        List<TooManyMethods> TooManyMethodss = TooManyMethods.extractTooManyMethods(
                directory.toString(),
                commit.getHash());

        // List<LongMethod> longMethods = LongMethod.extractLongMethod(
        // directory.toString(),
        // commit.getHash());

    }

}
