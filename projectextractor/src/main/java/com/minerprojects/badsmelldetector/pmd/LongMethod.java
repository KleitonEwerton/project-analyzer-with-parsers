package com.minerprojects.badsmelldetector.pmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.minerprojects.CommitReporter;
import com.minerprojects.PMDReporter;
import com.minerprojects.badsmelldetector.violation.ViolationPMD2;
import com.minerprojects.data.DataPMD;
import org.springframework.web.client.RestTemplate;

public class LongMethod extends BadSmellPMD2 {
    private static Logger logger = Logger.getLogger(LongMethod.class.getName());

    @Override
    public String toString() {
        return "LongMethod{" + super.toString() + '}';
    }

    public LongMethod() {
    }

    public LongMethod(ViolationPMD2 violationPMD2) {
        super(violationPMD2);
    }

    public static List<LongMethod> extractLongMethod(String projectDirectory, String projectName,
            CommitReporter commit) {

        String dir = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));
        RestTemplate restTemplate = new RestTemplate();
        try {
            PMDReporter.analyzeFile(dir, projectName, "NcssMethodCount").forEach(violation -> {

                DataPMD pmd = new DataPMD();
                pmd.setProjectName(projectName);
                pmd.setHash(commit.getHash());
                pmd.setHashPackageClass(violation.getAdditionalInfo().get("packageName") + "."
                        + violation.getAdditionalInfo().get("className"));
                pmd.setType(violation.getRule().getName());
                pmd.setBeginLine(violation.getBeginLine());
                pmd.setEndLine(violation.getEndLine());
                pmd.setBeginColumn(violation.getBeginColumn());
                pmd.setPriority(violation.getRule().getPriority().toString());
                pmd.setParentHash(commit.getParentHash());

                try {
                    restTemplate.postForObject("http://localhost:8080/api/pmd", pmd, DataPMD.class);
                    logger.info("Dados enviados com sucesso para a API.");

                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Erro ao enviar dados para a API: " + e.getMessage(), e);
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<LongMethod> result = new ArrayList();
        return result;
    }
}
