package com.minerprojects.badsmelldetector.pmd;

import java.io.File;
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

    public static List<DataClass> extractDataClass(String projectDirectory, String version, String projectName) {

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
                    "category/java/design.xml/DataClass",
                    "-f",
                    "xml", "--cache",
                    path + "\\.pmdCache_" + projectName + "_DataClass" };
        } else {
            command = new String[] {
                    "sh",
                    "run.sh",
                    "pmd",
                    "--file-list",
                    projectDirectory + "\\java_files_list.txt",
                    "-R",
                    "category/java/design.xml/DataClass",
                    "-f",
                    "xml", "--cache",
                    path + "\\.pmdCache_" + projectName + "_DataClass" };
        }
        CMDOutput cmdArray = CMD.cmdArray(projectDirectory, command);

        String concat = new String();
        for (String string : cmdArray.getOutput()) {
            concat += string + "\n";
        }

        return new SaxDataClass().fazerParsing(concat).stream().map(data -> {
            data.setVersion(version);
            return data;
        }).collect(Collectors.toList());
    }
}
