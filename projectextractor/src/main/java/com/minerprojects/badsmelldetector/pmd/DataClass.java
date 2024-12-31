package com.minerprojects.badsmelldetector.pmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.client.RestTemplate;

import com.minerprojects.CommitReporter;
import com.minerprojects.PMDReporter;
import com.minerprojects.badsmelldetector.violation.ViolationPMD;
import com.minerprojects.data.DataPMD;

public class DataClass extends BadSmellPMD {

    private static Logger logger = Logger.getLogger(DataClass.class.getName());

    @Override
    public String toString() {
        return "DataClass{" + super.toString() + '}';
    }

    public DataClass() {
    }

    public DataClass(ViolationPMD violationPMD) {
        super(violationPMD);
    }

    public static List<DataClass> extractDataClass(String projectDirectory, String projectName, CommitReporter commit) {

        String dir = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));
        RestTemplate restTemplate = new RestTemplate();

        try {
            PMDReporter.analyzeFile(dir, projectName, "DataClass").forEach(violation -> {
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
                    logger.info(
                            "Dados enviados com sucesso para a API." + violation.getAdditionalInfo());

                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Erro ao enviar dados para a API: " + e.getMessage(), e);
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<DataClass> dataClasses = new ArrayList();

        return dataClasses;
    }
}
