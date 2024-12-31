/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.badsmelldetector.pmd;

import java.util.Date;
import java.util.Objects;

import com.minerprojects.badsmelldetector.violation.ViolationPMD;

public class BadSmellPMD {
    private ViolationPMD violationPMD;
    private String version;
    private Date identificationDate;
    private Date treatmentDate;

    @Override
    public String toString() {
        return "BadSmellPMD{" + "violationPMD=" + violationPMD + ", version=" + version + ", identificationDate="
                + identificationDate + ", treatmentDate=" + treatmentDate + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.violationPMD);
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
        final BadSmellPMD other = (BadSmellPMD) obj;
        if (!Objects.equals(this.violationPMD, other.violationPMD)) {
            return false;
        }
        return true;
    }

    public BadSmellPMD() {
        this.violationPMD = new ViolationPMD();
    }

    public BadSmellPMD(ViolationPMD violationPMD) {
        this.violationPMD = violationPMD;
    }

    public ViolationPMD getViolationPMD() {
        return violationPMD;
    }

    public void setViolationPMD(ViolationPMD violationPMD) {
        this.violationPMD = violationPMD;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getIdentificationDate() {
        return identificationDate;
    }

    public void setIdentificationDate(Date identificationDate) {
        this.identificationDate = identificationDate;
    }

    public Date getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(Date treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

}
