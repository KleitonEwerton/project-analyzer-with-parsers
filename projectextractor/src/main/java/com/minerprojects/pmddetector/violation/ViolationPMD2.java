/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.pmddetector.violation;

import java.util.Objects;

public class ViolationPMD2 extends ViolationPMD {
    private String methodName;

    @Override
    public String toString() {
        return super.toString() + ", methodName=" + methodName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.methodName);
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
        if (!super.equals(obj)) {
            return false;
        }
        final ViolationPMD2 other = (ViolationPMD2) obj;
        if (!Objects.equals(this.methodName, other.methodName)) {
            return false;
        }
        return true;
    }

    public ViolationPMD2() {
    }

    public ViolationPMD2(String methodName, String rule, String classFound, String content, String classPackage,
            String externalInfoUrl, int priority, int beginLine, int endLine, int beginColumn, int endColumn) {
        super(rule, classFound, content, classPackage, externalInfoUrl, priority, beginLine, endLine, beginColumn,
                endColumn);
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
