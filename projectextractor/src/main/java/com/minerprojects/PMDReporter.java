package com.minerprojects;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

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

                PMDReporter.getPMD(commit, projectName);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void getPMD(CommitReporter commit, String projectName) throws Exception {

        String command = "git checkout " + commit.getHash();

        CMD.cmd("tmp/" + projectName, command);

        CMD.cmd("tmp/" + projectName, "git branch", true);

        Path directory = Paths.get(ExecutionConfig.PROJECT_PATH);

        List<CyclomaticComplexity> allCyclomaticComplexity = CyclomaticComplexity.extractCyclomaticComplexity(
                directory.toString(),
                commit.getHash());

        List<DataClass> allDataClasss = DataClass.extractDataClass(
                directory.toString(),
                commit.getHash());

        List<GodClass> allGodClasss = GodClass.extractGodClass(
                directory.toString(),
                commit.getHash());

        List<LongMethod> allLongMethods = LongMethod.extractLongMethod(
                directory.toString(),
                commit.getHash());

        List<LongParameterList> allLongParameterList = LongParameterList.extractLongParameterList(
                directory.toString(),
                commit.getHash());

        List<TooManyFields> allTooManyFields = TooManyFields.extractTooManyFields(
                directory.toString(),
                commit.getHash());

        List<TooManyMethods> allTooManyMethodss = TooManyMethods.extractTooManyMethods(
                directory.toString(),
                commit.getHash());

    }

}
