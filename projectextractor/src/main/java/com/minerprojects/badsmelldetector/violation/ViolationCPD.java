/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.badsmelldetector.violation;

import java.util.Objects;

import com.minerprojects.badsmelldetector.utils.File;

public class ViolationCPD {
    private File file;
    private String codeDuplicateText;
    private int tokens;
    private int numLines;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.file);
        hash = 53 * hash + Objects.hashCode(this.codeDuplicateText);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ViolationCPD other = (ViolationCPD) obj;
        if (!Objects.equals(this.codeDuplicateText, other.codeDuplicateText)) {
            return false;
        }
        if (!Objects.equals(this.file, other.file)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ViolationCPD{" + "file=" + file + ", codeDuplicateText=" + codeDuplicateText + '}';
    }

    public ViolationCPD() {
    }

    public ViolationCPD(File file, String codeDuplicateText, int tokens, int numLines) {
        this.file = file;
        this.codeDuplicateText = codeDuplicateText;
        this.tokens = tokens;
        this.numLines = numLines;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getNumLines() {
        return numLines;
    }

    public void setNumLines(int numLines) {
        this.numLines = numLines;
    }

    public String getCodeDuplicateText() {
        return codeDuplicateText;
    }

    public void setCodeDuplicateText(String codeDuplicateText) {
        this.codeDuplicateText = codeDuplicateText;
    }
}
