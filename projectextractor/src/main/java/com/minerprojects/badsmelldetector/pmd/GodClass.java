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

public class GodClass extends BadSmellPMD {

    @Override
    public String toString() {
        return "GodClass{" + super.toString() + '}';
    }

    public GodClass() {
    }

    public GodClass(ViolationPMD violationPMD) {
        super(violationPMD);
    }

    public static List<GodClass> extractGodClass(String projectDirectory, String projectName) {

        String dir = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));

        try {
            PMDReporter.analyzeFile(dir, projectName, "GodClass");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<GodClass> result = new ArrayList();
        return result;
    }
}
