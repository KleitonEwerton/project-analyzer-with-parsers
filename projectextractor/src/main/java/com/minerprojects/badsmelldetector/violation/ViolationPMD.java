/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.minerprojects.badsmelldetector.violation;

import java.util.Objects;

public class ViolationPMD extends Violation {
    private String rule;
    private String classFound;
    private String content;
    private String classPackage;
    private String externalInfoUrl;
    private int priority;

    @Override
    public String toString() {
        return "ViolationPMD{(" + "classFound=" + classFound + ", classPackage=" + classPackage + ", content=" + content
                + ") ";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.classFound);
        hash = 37 * hash + Objects.hashCode(this.classPackage);
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
        final ViolationPMD other = (ViolationPMD) obj;
        if (!Objects.equals(this.classFound, other.classFound)) {
            return false;
        }
        if (!Objects.equals(this.classPackage, other.classPackage)) {
            return false;
        }
        return true;
    }

    public ViolationPMD() {
    }

    public ViolationPMD(String rule, String classFound, String content, String classPackage, String externalInfoUrl,
            int priority, int beginLine, int endLine, int beginColumn, int endColumn) {
        super(beginLine, endLine, beginColumn, endColumn);
        this.rule = rule;
        this.classFound = classFound;
        this.content = content;
        this.classPackage = classPackage;
        this.externalInfoUrl = externalInfoUrl;
        this.priority = priority;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getClassFound() {
        return classFound;
    }

    public void setClassFound(String classFound) {
        this.classFound = classFound;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(String classPackage) {
        this.classPackage = classPackage;
    }

    public String getExternalInfoUrl() {
        return externalInfoUrl;
    }

    public void setExternalInfoUrl(String externalInfoUrl) {
        this.externalInfoUrl = externalInfoUrl;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

}
