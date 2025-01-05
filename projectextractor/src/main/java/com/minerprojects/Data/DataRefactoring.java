package com.minerprojects.data;

import com.minerprojects.CommitReporter;

public class DataRefactoring {

    private String projectName;

    private String hash;

    private String hashPackage;

    private String hashPackageClass;

    private String type;

    public DataRefactoring(CommitReporter commit, String hpackage, String hclass, String type) {

        this.projectName = commit.getProjectName();

        this.hash = commit.getHash();

        this.hashPackage = commit.getHash() + "." + hpackage;

        this.hashPackageClass = commit.getHash() + "." + hpackage + "." + hclass;

        this.type = type;

    }

    /**
     * @return String return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return String return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return String return the hashPackage
     */
    public String getHashPackage() {
        return hashPackage;
    }

    /**
     * @param hashPackage the hashPackage to set
     */
    public void setHashPackage(String hashPackage) {
        this.hashPackage = hashPackage;
    }

    /**
     * @return String return the hashPackageClass
     */
    public String getHashPackageClass() {
        return hashPackageClass;
    }

    /**
     * @param hashPackageClass the hashPackageClass to set
     */
    public void setHashPackageClass(String hashPackageClass) {
        this.hashPackageClass = hashPackageClass;
    }

    /**
     * @return String return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
