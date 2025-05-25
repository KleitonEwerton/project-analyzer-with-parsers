package com.minerprojects.pmddetector.pmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.client.RestTemplate;

import com.minerprojects.CommitReporter;
import com.minerprojects.PMDReporter;
import com.minerprojects.data.DataPMD;
import com.minerprojects.pmddetector.violation.ViolationPMD;

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

        try {
            PMDReporter.analyzeFile(dir, projectName, "DataClass").forEach(violation -> {
                PMDReporter.save(violation, projectName, commit);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<DataClass> dataClasses = new ArrayList();

        return dataClasses;
    }
}
