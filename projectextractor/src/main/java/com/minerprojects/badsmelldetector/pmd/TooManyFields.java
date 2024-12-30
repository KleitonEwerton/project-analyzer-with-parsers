/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.badsmelldetector.pmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.minerprojects.PMDReporter;

import com.minerprojects.badsmelldetector.violation.ViolationPMD2;

public class TooManyFields extends BadSmellPMD2 {
    private static Logger logger = Logger.getLogger(TooManyFields.class.getName());

    @Override
    public String toString() {
        return "TooManyFields{" + super.toString() + '}';
    }

    public TooManyFields() {
    }

    public TooManyFields(ViolationPMD2 violationPMD2) {
        super(violationPMD2);
    }

    public static List<TooManyFields> extractTooManyFields(String projectDirectory, String projectName) {

        String dir = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));

        try {
            PMDReporter.analyzeFile(dir, projectName, "TooManyFields").forEach(violation -> {
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

        List<TooManyFields> dataClasses = new ArrayList();

        return dataClasses;
    }
}