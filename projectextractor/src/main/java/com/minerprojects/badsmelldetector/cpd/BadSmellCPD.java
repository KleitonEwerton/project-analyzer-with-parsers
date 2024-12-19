/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minerprojects.badsmelldetector.cpd;

import java.util.Date;
import java.util.Objects;

import com.minerprojects.badsmelldetector.violation.ViolationCPD;

public class BadSmellCPD {

    private ViolationCPD violationCPD;
    private String version;
    private Date identificationDate;
    private Date treatmentDate;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.violationCPD);
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
        final BadSmellCPD other = (BadSmellCPD) obj;
        if (!Objects.equals(this.violationCPD, other.violationCPD)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BadSmellCPD{" + "violationCPD=" + violationCPD + ", version=" + version + ", identificationDate="
                + identificationDate + ", treatmentDate=" + treatmentDate + '}';
    }

    public BadSmellCPD() {
        this.violationCPD = new ViolationCPD();
    }

    public BadSmellCPD(ViolationCPD violationCPD) {
        this.violationCPD = violationCPD;
    }

    public ViolationCPD getViolationCPD() {
        return violationCPD;
    }

    public void setViolationCPD(ViolationCPD violationCPD) {
        this.violationCPD = violationCPD;
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
