package com.minerprojects.badsmelldetector.pmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.minerprojects.PMDReporter;
import com.minerprojects.badsmelldetector.violation.ViolationPMD2;

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

    public static List<LongMethod> extractLongMethod(String projectDirectory, String projectName) {

        String dir = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));

        try {
            PMDReporter.analyzeFile(dir, projectName, "NcssMethodCount").forEach(violation -> {
                logger.info(
                        "VIOLATION: "
                                + violation.getDescription() + " "
                                + violation.getBeginLine() + " "
                                + violation.getEndLine() + " "
                                + violation.getBeginColumn() + " "
                                + violation.getRule().getName() + " "
                                + violation.getRule().getPriority() + " "
                                + violation.getAdditionalInfo().get("packageName"));
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<LongMethod> result = new ArrayList();
        return result;
    }
}
