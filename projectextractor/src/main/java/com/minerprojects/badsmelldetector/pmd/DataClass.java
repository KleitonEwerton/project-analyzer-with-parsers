package com.minerprojects.badsmelldetector.pmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.minerprojects.PMDReporter;
import com.minerprojects.badsmelldetector.violation.ViolationPMD;

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

    public static List<DataClass> extractDataClass(String projectDirectory, String projectName) {

        String dir = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));

        try {
            PMDReporter.analyzeFile(dir, projectName, "DataClass").forEach(violation -> {
                logger.info(
                        "VIOLATION: "
                                + violation.getDescription() + " "
                                + violation.getBeginLine() + " "
                                + violation.getRule().getName() + " "
                                + violation.getRule().getPriority() + " "
                                + violation.getAdditionalInfo().get("packageName"));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<DataClass> dataClasses = new ArrayList();

        return dataClasses;
    }
}
