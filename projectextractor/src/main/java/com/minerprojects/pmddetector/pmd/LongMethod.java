package com.minerprojects.pmddetector.pmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.minerprojects.CommitReporter;
import com.minerprojects.PMDReporter;
import com.minerprojects.data.DataPMD;
import com.minerprojects.pmddetector.violation.ViolationPMD2;

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
