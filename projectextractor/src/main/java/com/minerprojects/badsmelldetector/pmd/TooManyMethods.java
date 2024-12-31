/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.badsmelldetector.pmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.client.RestTemplate;

import com.minerprojects.CommitReporter;
import com.minerprojects.PMDReporter;
import com.minerprojects.badsmelldetector.violation.ViolationPMD;
import com.minerprojects.data.DataPMD;

public class TooManyMethods extends BadSmellPMD {
    private static Logger logger = Logger.getLogger(TooManyMethods.class.getName());
    RestTemplate restTemplate = new RestTemplate();

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
            String projectName, CommitReporter commit) {

        String dir = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));
        List<TooManyMethods> result = new ArrayList();

        try {
            PMDReporter.analyzeFile(dir, projectName, "TooManyMethods").forEach(violation -> {
                PMDReporter.save(violation, projectName, commit);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
