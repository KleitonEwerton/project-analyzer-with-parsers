/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.pmddetector.pmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.minerprojects.CommitReporter;
import com.minerprojects.PMDReporter;
import com.minerprojects.pmddetector.violation.ViolationPMD2;

public class CyclomaticComplexity extends BadSmellPMD2 {

    @Override
    public String toString() {
        return "CyclomaticComplexity{" + super.toString() + '}';
    }

    public CyclomaticComplexity() {
    }

    public CyclomaticComplexity(ViolationPMD2 violationPMD2) {
        super(violationPMD2);
    }

    public static List<CyclomaticComplexity> extractCyclomaticComplexity(String projectDirectory,
            String projectName, CommitReporter commit) {

        String dir = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));
        List<CyclomaticComplexity> result = new ArrayList();

        try {

            PMDReporter.analyzeFile(dir, projectName, "CyclomaticComplexity").forEach(violation -> {
                PMDReporter.save(violation, projectName, commit);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
