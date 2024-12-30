/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.badsmelldetector.pmd;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import com.minerprojects.badsmelldetector.ExecutionConfig;
import com.minerprojects.badsmelldetector.cmd.CMD;
import com.minerprojects.badsmelldetector.cmd.CMDOutput;
import com.minerprojects.badsmelldetector.violation.ViolationPMD;
import com.minerprojects.badsmelldetector.xml.SaxGodClass;

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

    public static List<GodClass> extractGodClass(String projectDirectory, String version, String projectName) {

        String os = System.getProperty("os.name");
        String[] command = null;
        String path = projectDirectory.substring(0, projectDirectory.lastIndexOf(File.separator));

        if (os.contains("Windows")) {
            command = new String[] {
                    "cmd",
                    "/c",
                    "pmd.bat",
                    "check",
                    "--file-list",
                    projectDirectory + "\\java_files_list.txt",
                    "-R",
                    "category/java/design.xml/GodClass",
                    "-f",
                    "xml", "--cache",
                    path + "\\.pmdCache_" + projectName + "_GodClass" };
        } else {
            command = new String[] {
                    "sh",
                    "run.sh",
                    "pmd",
                    "--file-list",
                    projectDirectory + "\\java_files_list.txt",
                    "-R",
                    "category/java/design.xml/GodClass",
                    "-f",
                    "xml", "--cache",
                    path + "\\.pmdCache_" + projectName + "_GodClass" };
        }
        CMDOutput cmdArray = CMD.cmdArray(ExecutionConfig.PMD_PATH, command);

        String concat = new String();
        for (String string : cmdArray.getOutput()) {
            concat += string + "\n";
        }

        return new SaxGodClass().fazerParsing(concat).stream().map(data -> {
            data.setVersion(version);
            return data;
        }).collect(Collectors.toList());
    }
}
