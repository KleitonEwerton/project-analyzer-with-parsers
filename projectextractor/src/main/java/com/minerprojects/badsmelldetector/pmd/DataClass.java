package com.minerprojects.badsmelldetector.pmd;

import java.util.List;
import java.util.stream.Collectors;

import com.minerprojects.badsmelldetector.cmd.CMD;
import com.minerprojects.badsmelldetector.cmd.CMDOutput;
import com.minerprojects.badsmelldetector.violation.ViolationPMD;
import com.minerprojects.badsmelldetector.xml.SaxDataClass;

public class DataClass extends BadSmellPMD {

    @Override
    public String toString() {
        return "DataClass{" + super.toString() + '}';
    }

    public DataClass() {
    }

    public DataClass(ViolationPMD violationPMD) {
        super(violationPMD);
    }

    public static List<DataClass> extractDataClass(String projectDirectory, String version) {
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
                    "category/java/design.xml/DataClass",
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
                    "category/java/design.xml/DataClass",
                    "-f",
                    "xml" };
        }
        CMDOutput cmdArray = CMD.cmdArray(projectDirectory, command);

        String concat = new String();
        for (String string : cmdArray.getOutput()) {
            concat += string + "\n";
        }

        SaxDataClass sax = new SaxDataClass();
        List<DataClass> datasClass = sax.fazerParsing(concat).stream().map(data -> {
            data.setVersion(version);
            return data;
        }).collect(Collectors.toList());

        return datasClass;
    }
}
