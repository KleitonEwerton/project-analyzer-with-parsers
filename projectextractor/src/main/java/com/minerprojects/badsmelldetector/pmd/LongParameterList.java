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
import com.minerprojects.badsmelldetector.violation.ViolationPMD2;
import com.minerprojects.data.DataPMD;

public class LongParameterList extends BadSmellPMD2 {
    private static Logger logger = Logger.getLogger(LongParameterList.class.getName());

    @Override
    public String toString() {
        return "LongParameterList{" + super.toString() + '}';
    }

    public LongParameterList() {
    }

    public LongParameterList(ViolationPMD2 violationPMD2) {
        super(violationPMD2);
    }

    public static List<LongParameterList> extractLongParameterList(String projectDirectory, String projectName,
            CommitReporter commit) {

        String dir = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));
        List<LongParameterList> result = new ArrayList();

        try {
            PMDReporter.analyzeFile(dir, projectName, "ExcessiveParameterList").forEach(violation -> {
                PMDReporter.save(violation, projectName, commit);

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}