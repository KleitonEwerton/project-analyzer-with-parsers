/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.pmddetector.utils;

import java.util.Objects;

import com.minerprojects.pmddetector.violation.Violation;

public class File {
    private Violation violation;
    private String path;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.path);
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
        final File other = (File) obj;
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return path;
    }

    public File() {
        this.violation = new Violation();
        this.path = new String();
    }

    public File(Violation violation, String path) {
        this.violation = violation;
        this.path = path;
    }

    public Violation getViolation() {
        return violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
