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

public class GodClass extends BadSmellPMD {

    private static Logger logger = Logger.getLogger(GodClass.class.getName());

    @Override
    public String toString() {
        return "GodClass{" + super.toString() + '}';
    }

    public GodClass() {
    }

    public GodClass(ViolationPMD violationPMD) {
        super(violationPMD);
    }

    public static List<GodClass> extractGodClass(String projectDirectory, String projectName, CommitReporter commit) {

        String dir = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));
        List<GodClass> result = new ArrayList();

        try {
            PMDReporter.analyzeFile(dir, projectName, "GodClass").forEach(violation -> {
                PMDReporter.save(violation, projectName, commit);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
