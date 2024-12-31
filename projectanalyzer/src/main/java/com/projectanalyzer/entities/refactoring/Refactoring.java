package com.projectanalyzer.entities.refactoring;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Refactoring {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    private String hashPackage;

    @Column(nullable = false)
    private String hashPackageClass;

    @Column(nullable = false)
    private String type;

    @Column(nullable = true)
    private String parentHash;

    @Column(nullable = true)
    private String parentHashPackage;

    @Column(nullable = true)
    private String parentHashPackageClass;

    /**
     * @return long return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
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
