package com.minerprojects.badsmelldetector.pmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.minerprojects.PMDReporter;
import com.minerprojects.badsmelldetector.violation.ViolationPMD;

public class DataClass extends BadSmellPMD {

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

        String path = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));

        try {
            PMDReporter.analyzeFile(path + "\\" + projectName, projectName, "DataClass");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<DataClass> dataClasses = new ArrayList();

        return dataClasses;
    }
}
