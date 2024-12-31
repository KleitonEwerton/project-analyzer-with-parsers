package com.minerprojects.data;

public class DataRefactoring {

    private String projectName;

    private String hash;

    private String hashPackage;

    private String hashPackageClass;

    private String type;

    private String parentHash;

    private String parentHashPackage;

    private String parentHashPackageClass;

    public DataRefactoring() {
        // Constructor is intentionally empty
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

    /**
     * @return String return the parentHash
     */
    public String getParentHash() {
        return parentHash;
    }

    /**
     * @param parentHash the parentHash to set
     */
    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
    }

    /**
     * @return String return the parentHashPackage
     */
    public String getParentHashPackage() {
        return parentHashPackage;
    }

    /**
     * @param parentHashPackage the parentHashPackage to set
     */
    public void setParentHashPackage(String parentHashPackage) {
        this.parentHashPackage = parentHashPackage;
    }

    /**
     * @return String return the parentHashPackageClass
     */
    public String getParentHashPackageClass() {
        return parentHashPackageClass;
    }

    /**
     * @param parentHashPackageClass the parentHashPackageClass to set
     */
    public void setParentHashPackageClass(String parentHashPackageClass) {
        this.parentHashPackageClass = parentHashPackageClass;
    }

}
