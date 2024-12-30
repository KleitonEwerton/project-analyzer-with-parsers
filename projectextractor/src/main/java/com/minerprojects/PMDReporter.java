package com.minerprojects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import com.minerprojects.CLI.CLIExecute;
import com.minerprojects.badsmelldetector.ExecutionConfig;
import com.minerprojects.badsmelldetector.pmd.CyclomaticComplexity;
import com.minerprojects.badsmelldetector.pmd.DataClass;
import com.minerprojects.badsmelldetector.pmd.LongMethod;
import com.minerprojects.badsmelldetector.pmd.TooManyMethods;
import com.minerprojects.badsmelldetector.pmd.GodClass;

public class PMDReporter {

        private static final Logger logger = Logger.getLogger(PMDReporter.class.getName());

        public static void getAllPMD(String projectName) {

                for (CommitReporter commit : CommitReporter.commits) {

                        try {

                                if (logger.isLoggable(java.util.logging.Level.INFO)) {
                                        logger.info(String.format("check PMD - tmp/%s/%s", projectName,
                                                        commit.getHash()));
                                }

                                long tempoInicial = System.currentTimeMillis();

                                saveJavaFiles(commit.getJavaFiles(), projectName);

                                PMDReporter.getPMD(commit, projectName);

                                PMDReporter.getPMDParent(commit, projectName);

                                long tempoFinal = System.currentTimeMillis();

                                long tempoDecorrido = (tempoFinal - tempoInicial) / 1000;
                                logger.info(
                                                String.format("Tempo para analisar o commit %s: %d s", commit.getHash(),
                                                                tempoDecorrido));

                        } catch (Exception e) {
                                e.printStackTrace();
                        }

                }
        }

        public static void getPMD(CommitReporter commit, String projectName) throws Exception {

                String command = "git checkout " + commit.getHash();

                CLIExecute.executeCheckout(command, "tmp" + File.separator + projectName);

                String directory = Paths.get(ExecutionConfig.PROJECT_PATH).toString();

                CyclomaticComplexity.extractCyclomaticComplexity(
                                directory,
                                commit.getHash(), projectName);

                DataClass.extractDataClass(
                                directory,
                                commit.getHash(), projectName);

                GodClass.extractGodClass(
                                directory,
                                commit.getHash(), projectName);

                LongMethod.extractLongMethod(
                                directory,
                                commit.getHash(), projectName);

                TooManyMethods.extractTooManyMethods(
                                directory,
                                commit.getHash(), projectName);

        }

        public static void getPMDParent(CommitReporter commit, String projectName) throws Exception {

                String command = "git checkout " + commit.getParentHash();

                CLIExecute.executeCheckout(command, "tmp" + File.separator + projectName);

                String directory = Paths.get(ExecutionConfig.PROJECT_PATH).toString();

                CyclomaticComplexity.extractCyclomaticComplexity(
                                directory,
                                commit.getHash(), projectName);

                DataClass.extractDataClass(
                                directory,
                                commit.getHash(), projectName);

                GodClass.extractGodClass(
                                directory,
                                commit.getHash(), projectName);

                LongMethod.extractLongMethod(
                                directory,
                                commit.getHash(), projectName);

                TooManyMethods.extractTooManyMethods(
                                directory,
                                commit.getHash(), projectName);

        }

        public static void saveJavaFiles(List<String> javaFiles, String projectName) {

                String tmpDir = "tmp/" + projectName;

                File directory = new File(tmpDir);
                if (!directory.exists()) {
                        directory.mkdirs();
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(tmpDir + "/java_files_list.txt"))) {
                        for (String javaFile : javaFiles) {
                                writer.write(javaFile);
                                writer.newLine();
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

}
