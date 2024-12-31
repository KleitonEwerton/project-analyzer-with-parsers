package com.minerprojects.pmddetector.pmd;

import java.util.Date;
import java.util.Objects;

import com.minerprojects.pmddetector.violation.ViolationPMD2;

public class BadSmellPMD2 {

    private ViolationPMD2 violationPMD2;
    private String version;
    private Date identificationDate;
    private Date treatmentDate;

    @Override
    public String toString() {
        return "BadSmellPMD2{" + "violationPMD2=" + violationPMD2 + ", version=" + version + ", identificationDate="
                + identificationDate + ", treatmentDate=" + treatmentDate + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.violationPMD2);
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
        final BadSmellPMD2 other = (BadSmellPMD2) obj;
        if (!Objects.equals(this.violationPMD2, other.violationPMD2)) {
            return false;
        }
        return true;
    }

    public BadSmellPMD2() {
        this.violationPMD2 = new ViolationPMD2();
    }

    public BadSmellPMD2(ViolationPMD2 violationPMD2) {
        this.violationPMD2 = violationPMD2;
    }

    public ViolationPMD2 getViolationPMD2() {
        return violationPMD2;
    }

    public void setViolationPMD2(ViolationPMD2 violationPMD2) {
        this.violationPMD2 = violationPMD2;
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
