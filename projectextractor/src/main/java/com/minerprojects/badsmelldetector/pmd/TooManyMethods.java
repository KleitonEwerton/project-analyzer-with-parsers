/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.badsmelldetector.pmd;

import java.util.List;
import java.util.stream.Collectors;

import com.minerprojects.CommitReporter;
import com.minerprojects.badsmelldetector.ExecutionConfig;
import com.minerprojects.badsmelldetector.cmd.CMD;
import com.minerprojects.badsmelldetector.cmd.CMDOutput;
import com.minerprojects.badsmelldetector.violation.ViolationPMD;
import com.minerprojects.badsmelldetector.xml.SaxTooManyMethods;

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

    public static List<TooManyMethods> extractTooManyMethods(String projectDirectory, String commitHash) {

        String os = System.getProperty("os.name");

        String[] command = null;

        if (os.contains("Windows")) {

            command = new String[] {
                    "cmd",
                    "/c",
                    "pmd.bat",
                    "check",
                    "-d",
                    projectDirectory,
                    "-R",
                    "category/java/design.xml/TooManyMethods",
                    "-f",
                    "xml",
                    "--cache", "../tmp/pmdCache" };

        } else {
            command = new String[] {
                    "sh",
                    "run.sh",
                    "pmd",
                    "-d",
                    projectDirectory,
                    "-R",
                    "category/java/design.xml/TooManyMethods",
                    "-f",
                    "xml",
                    "--cache", "../tmp/pmdCache" };
        }

        CMDOutput cmdArray = CMD.cmdArray(ExecutionConfig.PMD_PATH, command);

        String concat = new String();

        for (String string : cmdArray.getOutput()) {
            concat += string + "\n";
        }

        SaxTooManyMethods sax = new SaxTooManyMethods();

        List<TooManyMethods> tooManyMethodss = sax.fazerParsing(concat).stream().map(l -> {
            l.setVersion(commitHash);
            return l;
        }).collect(Collectors.toList());

        return tooManyMethodss;
    }
}
