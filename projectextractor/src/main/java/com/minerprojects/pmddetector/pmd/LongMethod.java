package com.minerprojects.pmddetector.pmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.minerprojects.CommitReporter;
import com.minerprojects.PMDReporter;
import com.minerprojects.pmddetector.violation.ViolationPMD2;

public class LongMethod extends BadSmellPMD2 {

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
        List<LongMethod> result = new ArrayList();

        try {
            PMDReporter.analyzeFile(dir, projectName, "NcssMethodCount").forEach(violation -> {

                PMDReporter.save(violation, projectName, commit);

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
