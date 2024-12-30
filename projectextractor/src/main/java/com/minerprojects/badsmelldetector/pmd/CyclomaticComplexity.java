/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.badsmelldetector.pmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.minerprojects.PMDReporter;
import com.minerprojects.badsmelldetector.violation.ViolationPMD2;

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
            String projectName) {

        String path = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));

        try {

            PMDReporter.analyzeFile(path + "\\" + projectName, projectName, "CyclomaticComplexity");

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<CyclomaticComplexity> result = new ArrayList();
        return result;
    }
}
