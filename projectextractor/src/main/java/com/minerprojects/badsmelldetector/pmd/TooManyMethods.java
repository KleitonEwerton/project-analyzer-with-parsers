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
import com.minerprojects.badsmelldetector.violation.ViolationPMD;

public class TooManyMethods extends BadSmellPMD {

    @Override
    public String toString() {
        return "TooManyMethods{" + super.toString() + '}';
    }

    public TooManyMethods() {
    }

    public TooManyMethods(ViolationPMD violationPMD) {
        super(violationPMD);
    }

    public static List<TooManyMethods> extractTooManyMethods(String projectDirectory,
            String projectName) {

        String dir = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));

        try {
            PMDReporter.analyzeFile(dir, projectName, "TooManyMethods");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<TooManyMethods> result = new ArrayList();
        return result;
    }
}
