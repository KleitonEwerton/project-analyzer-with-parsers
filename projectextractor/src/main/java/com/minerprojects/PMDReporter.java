package com.minerprojects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import com.minerprojects.CLI.CLIExecute;
import com.minerprojects.badsmelldetector.ExecutionConfig;
import com.minerprojects.badsmelldetector.pmd.CyclomaticComplexity;
import com.minerprojects.badsmelldetector.pmd.DataClass;
import com.minerprojects.badsmelldetector.pmd.LongMethod;
import com.minerprojects.badsmelldetector.pmd.LongParameterList;
import com.minerprojects.badsmelldetector.pmd.TooManyFields;
import com.minerprojects.badsmelldetector.pmd.TooManyMethods;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PmdAnalysis;
import net.sourceforge.pmd.lang.document.FileCollector;
import net.sourceforge.pmd.lang.rule.RuleSet;
import net.sourceforge.pmd.lang.rule.RuleSetLoader;
import net.sourceforge.pmd.reporting.RuleViolation;

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

                                saveJavaFiles(commit.getJavaFiles(), projectName);

                                PMDReporter.getPMD(commit, projectName);

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
                                projectName);

                DataClass.extractDataClass(
                                directory,
                                projectName);

                GodClass.extractGodClass(
                                directory,
                                projectName);

                LongMethod.extractLongMethod(
                                directory,
                                projectName);

                TooManyMethods.extractTooManyMethods(
                                directory,
                                projectName);

                LongParameterList.extractLongParameterList(
                                directory,
                                projectName);

                TooManyFields.extractTooManyFields(
                                directory,
                                projectName);

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

        public static List<RuleViolation> analyzeFile(final String dir, String projectName, String analiserName)
                        throws IOException {

                PMDConfiguration config = new PMDConfiguration();

                final String path = dir + "\\" + projectName;

                config.setAnalysisCacheLocation(
                                Paths.get(dir, ".pmdCache_" + projectName + "_" + analiserName).toString());
                config.setReportFormat("xml");

                Path javaFilesListPath = Paths.get(path, "java_files_list.txt");

                try (PmdAnalysis analysis = PmdAnalysis.create(config)) {
                        // Carregar a regra CyclomaticComplexity
                        RuleSetLoader ruleSetLoader = analysis.newRuleSetLoader();
                        RuleSet ruleSet = ruleSetLoader
                                        .loadFromResource("category/java/design.xml/" + analiserName);
                        analysis.addRuleSet(ruleSet);

                        FileCollector fileCollector = analysis.files();

                        Files.lines(javaFilesListPath).forEach(file -> {
                                fileCollector.addFile(Paths.get(path, file));
                        });
                        return analysis.performAnalysisAndCollectReport().getViolations();

                }
        }

}
