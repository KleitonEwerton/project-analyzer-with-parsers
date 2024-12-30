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

    public static List<LongMethod> extractLongMethod(String projectDirectory, String projectName) {

        String dir = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));

        try {
            PMDReporter.analyzeFile(dir, projectName, "NcssMethodCount");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<LongMethod> result = new ArrayList();
        return result;
    }
}
