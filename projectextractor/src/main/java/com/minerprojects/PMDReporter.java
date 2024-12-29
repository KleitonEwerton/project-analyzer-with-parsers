package com.minerprojects;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import com.minerprojects.CLI.CLIExecute;
import com.minerprojects.CLI.CLIExecution;
import com.minerprojects.badsmelldetector.ExecutionConfig;
import com.minerprojects.badsmelldetector.cmd.CMD;
import com.minerprojects.badsmelldetector.pmd.CyclomaticComplexity;
import com.minerprojects.badsmelldetector.pmd.DataClass;
import com.minerprojects.badsmelldetector.pmd.LongMethod;
import com.minerprojects.badsmelldetector.pmd.LongParameterList;
import com.minerprojects.badsmelldetector.pmd.TooManyFields;
import com.minerprojects.badsmelldetector.pmd.TooManyMethods;
import com.minerprojects.badsmelldetector.pmd.GodClass;

public class PMDReporter {
    private static final Logger logger = Logger.getLogger(MinerProjects.class.getName());

    public static void getAllPMD(String projectName) {

        for (CommitReporter commit : CommitReporter.commits) {

            try {

                if (logger.isLoggable(java.util.logging.Level.INFO)) {
                    logger.info(String.format("check PMD - tmp/%s/%s", projectName, commit.getHash()));
                }

                System.out.println("STARTING 1");
                PMDReporter.getPMD(commit, projectName);
                System.out.println("FINISHING 1");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void getPMD(CommitReporter commit, String projectName) throws Exception {

        String command = "git checkout " + commit.getHash();

        CLIExecute.executeCheckout(command, "tmp" + File.separator + projectName);

        Path directory = Paths.get(ExecutionConfig.PROJECT_PATH);

        // List<CyclomaticComplexity> allCyclomaticComplexity =
        // CyclomaticComplexity.extractCyclomaticComplexity(
        // directory.toString(),
        // commit.getHash());

        // List<DataClass> allDataClasss = DataClass.extractDataClass(
        // directory.toString(),
        // commit.getHash());

        // List<GodClass> allGodClasss = GodClass.extractGodClass(
        // directory.toString(),
        // commit.getHash());

        // List<LongMethod> allLongMethods = LongMethod.extractLongMethod(
        // directory.toString(),
        // commit.getHash());

        // List<LongParameterList> allLongParameterList =
        // LongParameterList.extractLongParameterList(
        // directory.toString(),
        // commit.getHash());

        // List<TooManyFields> allTooManyFields = TooManyFields.extractTooManyFields(
        // directory.toString(),
        // commit.getHash());

        TooManyMethods.extractTooManyMethods(directory.toString(), commit.getHash());

    }

}
