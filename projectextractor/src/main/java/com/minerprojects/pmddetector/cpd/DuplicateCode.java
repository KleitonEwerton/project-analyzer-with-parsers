package com.minerprojects.pmddetector.cpd;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.minerprojects.pmddetector.ExecutionConfig;
import com.minerprojects.pmddetector.cmd.CMD;
import com.minerprojects.pmddetector.cmd.CMDOutput;
import com.minerprojects.pmddetector.violation.ViolationCPD;
import com.minerprojects.pmddetector.xml.SaxDuplicateCode;

public class DuplicateCode extends BadSmellCPD {

    @Override
    public String toString() {
        return "DuplicateCode{" + super.toString() + '}';
    }

    public DuplicateCode() {
    }

    public DuplicateCode(ViolationCPD violationCPD) {
        super(violationCPD);
    }

    /**
     * 
     * @param projectDirectory
     * @param version
     * @return
     */
    public static List<DuplicateCode> extractDuplicateCode(String projectDirectory, String version) {

        String os = System.getProperty("os.name");
        String[] command = null;

        if (os.contains("Windows")) {
            command = new String[] {
                    "cmd",
                    "/c",
                    "cpd.bat",
                    "--files",
                    projectDirectory,
                    "--minimum-tokens",
                    "100",
                    "--format",
                    "xml"
            };
        } else {
            command = new String[] {
                    "sh",
                    "run.sh",
                    "cpd",
                    "--files",
                    projectDirectory,
                    "--minimum-tokens",
                    "100",
                    "--format",
                    "xml"
            };
        }
        CMDOutput cmdArray = CMD.cmdArray(ExecutionConfig.PMD_PATH, command);

        String concat = new String();
        for (String string : cmdArray.getOutput()) {
            concat += string + "\n";
        }

        List<DuplicateCode> duplicatedCodes = new ArrayList<>();

        if (concat.contains("<pmd-cpd>") && concat.contains("</pmd-cpd>")) {
            SaxDuplicateCode sax = new SaxDuplicateCode();
            duplicatedCodes = sax.fazerParsing(concat).stream().map(l -> {
                l.setVersion(version);
                return l;
            }).collect(Collectors.toList());
        }

        return duplicatedCodes;
    }
}
