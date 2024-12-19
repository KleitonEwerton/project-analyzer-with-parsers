/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.badsmelldetector.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.minerprojects.badsmelldetector.cpd.DuplicateCode;
import com.minerprojects.badsmelldetector.pmd.DataClass;
import com.minerprojects.badsmelldetector.pmd.GodClass;
import com.minerprojects.badsmelldetector.pmd.LongMethod;
import com.minerprojects.badsmelldetector.pmd.LongParameterList;

public class ProjectVersions {

    private List<DuplicateCode> duplicateCodes;
    private List<DataClass> dataClasses;
    private List<GodClass> godClasses;
    private List<LongMethod> longMethods;
    private List<LongParameterList> parametersList;
    private String version;
    private Date date;

    @Override
    public String toString() {
        return "ProjectVersions{" + "dataClasses=" + dataClasses + ", godClasses=" + godClasses + ", longMethods="
                + longMethods + ", parametersList=" + parametersList + ", version=" + version + ", date=" + date + '}';
    }

    public ProjectVersions() {
        this.dataClasses = new ArrayList<>();
        this.duplicateCodes = new ArrayList<>();
        this.godClasses = new ArrayList<>();
        this.longMethods = new ArrayList<>();
        this.parametersList = new ArrayList<>();
    }

    public ProjectVersions(List<DuplicateCode> duplicateCodes, List<DataClass> dataClasses, List<GodClass> godClasses,
            List<LongMethod> longMethods, List<LongParameterList> parametersList) {
        this.duplicateCodes = duplicateCodes;
        this.dataClasses = dataClasses;
        this.godClasses = godClasses;
        this.longMethods = longMethods;
        this.parametersList = parametersList;
    }

    public List<DuplicateCode> getDuplicateCodes() {
        return duplicateCodes;
    }

    public void setDuplicateCodes(List<DuplicateCode> duplicateCodes) {
        this.duplicateCodes = duplicateCodes;
    }

    public List<DataClass> getDataClasses() {
        return dataClasses;
    }

    public void setDataClasses(List<DataClass> dataClasses) {
        this.dataClasses = dataClasses;
    }

    public List<GodClass> getGodClasses() {
        return godClasses;
    }

    public void setGodClasses(List<GodClass> godClasses) {
        this.godClasses = godClasses;
    }

    public List<LongMethod> getLongMethods() {
        return longMethods;
    }

    public void setLongMethods(List<LongMethod> longMethods) {
        this.longMethods = longMethods;
    }

    public List<LongParameterList> getParametersList() {
        return parametersList;
    }

    public void setParametersList(List<LongParameterList> parametersList) {
        this.parametersList = parametersList;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
