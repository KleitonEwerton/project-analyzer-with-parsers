/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.badsmelldetector.pmd;

import java.util.List;
import java.util.stream.Collectors;

import com.minerprojects.badsmelldetector.ExecutionConfig;
import com.minerprojects.badsmelldetector.cmd.CMD;
import com.minerprojects.badsmelldetector.cmd.CMDOutput;
import com.minerprojects.badsmelldetector.violation.ViolationPMD2;
import com.minerprojects.badsmelldetector.xml.SaxLongMethod;

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

    public static List<LongMethod> extractLongMethod(String projectDirectory, String version) {

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
                    "category/java/design.xml/NcssMethodCount",
                    "-f",
                    "xml" };
        } else {
            command = new String[] {
                    "sh",
                    "run.sh",
                    "pmd",
                    "-d",
                    projectDirectory,
                    "-R",
                    "category/java/design.xml/NcssMethodCount",
                    "-f",
                    "xml" };
        }
        CMDOutput cmdArray = CMD.cmdArray(ExecutionConfig.PMD_PATH, command);

        String concat = new String();
        for (String string : cmdArray.getOutput()) {
            concat += string + "\n";
        }

        SaxLongMethod sax = new SaxLongMethod();
        List<LongMethod> longMethods = sax.fazerParsing(concat).stream().map(l -> {
            l.setVersion(version);
            return l;
        }).collect(Collectors.toList());

        longMethods.forEach(l -> {
            System.out.println(l);

        });

        return longMethods;
    }
}
