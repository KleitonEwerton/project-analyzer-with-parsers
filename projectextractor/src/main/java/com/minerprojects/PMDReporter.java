package com.minerprojects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.client.RestTemplate;

import com.minerprojects.cli.CLIExecute;
import com.minerprojects.cli.CLIExecution;
import com.minerprojects.data.DataPMD;
import com.minerprojects.pmddetector.ExecutionConfig;
import com.minerprojects.pmddetector.pmd.CyclomaticComplexity;
import com.minerprojects.pmddetector.pmd.DataClass;
import com.minerprojects.pmddetector.pmd.GodClass;
import com.minerprojects.pmddetector.pmd.LongMethod;
import com.minerprojects.pmddetector.pmd.LongParameterList;
import com.minerprojects.pmddetector.pmd.TooManyFields;
import com.minerprojects.pmddetector.pmd.TooManyMethods;

import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PmdAnalysis;
import net.sourceforge.pmd.lang.document.FileCollector;
import net.sourceforge.pmd.lang.rule.RuleSet;
import net.sourceforge.pmd.lang.rule.RuleSetLoader;
import net.sourceforge.pmd.reporting.RuleViolation;

public class PMDReporter {

        // Private constructor to hide the implicit public one
        private PMDReporter() {
                throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }

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

                        } catch (IOException e) {
                                e.printStackTrace();
                        } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                e.printStackTrace();
                        }

                }
        }

        public static void getPMD(CommitReporter commit, String projectName) throws IOException, InterruptedException {

                String command = "git checkout " + commit.getHash();

                CLIExecution execute = CLIExecute.executeCheckout(command, "tmp" + File.separator + projectName);

                if (!execute.getError().isEmpty()) {

                        logger.info(String.format("ERROR%n%s%n%s", command, execute.toString()));

                        new CommitError(projectName,
                                        commit.getHash(),
                                        command + "\n" + execute.toString());

                        return;

                }

                String directory = Paths.get(ExecutionConfig.PROJECT_PATH).toString();

                CyclomaticComplexity.extractCyclomaticComplexity(
                                directory,
                                projectName, commit);

                DataClass.extractDataClass(
                                directory,
                                projectName, commit);

                GodClass.extractGodClass(
                                directory,
                                projectName, commit);

                LongMethod.extractLongMethod(
                                directory,
                                projectName, commit);

                TooManyMethods.extractTooManyMethods(
                                directory,
                                projectName, commit);

                LongParameterList.extractLongParameterList(
                                directory,
                                projectName, commit);

                TooManyFields.extractTooManyFields(
                                directory,
                                projectName, commit);

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

                final String path = dir + File.separator + projectName;

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

                        Files.lines(javaFilesListPath).forEach(file -> fileCollector.addFile(Paths.get(path, file)));

                        return analysis.performAnalysisAndCollectReport().getViolations();

                }
        }

        public static void save(RuleViolation violation, String projectName, CommitReporter commit) {
                DataPMD pmd = new DataPMD();
                RestTemplate restTemplate = new RestTemplate();

                pmd.setProjectName(projectName);

                pmd.setHash(commit.getHash());

                pmd.setHashPackage(pmd.getHash() + "."
                                + violation.getAdditionalInfo().get("packageName"));

                pmd.setHashPackageClass(pmd.getHashPackage() + "."
                                + violation.getAdditionalInfo().get("className"));

                pmd.setType(violation.getRule().getName());

                pmd.setBeginLine(violation.getBeginLine());

                pmd.setEndLine(violation.getEndLine());

                pmd.setBeginColumn(violation.getBeginColumn());

                pmd.setPriority(violation.getRule().getPriority().toString());

                pmd.setParentHash(commit.getParentHash());

                pmd.setParentHashPackage(
                                pmd.getParentHash() + "." + violation.getAdditionalInfo().get("packageName"));

                pmd.setParentHashPackageClass(
                                pmd.getParentHashPackage() + "." + violation.getAdditionalInfo().get("className"));

                try {
                        restTemplate.postForObject("http://localhost:8080/api/pmd", pmd, DataPMD.class);

                } catch (Exception e) {
                        logger.log(Level.SEVERE, "Erro ao enviar dados para a API: " + e.getMessage(), e);
                }
        }

}
