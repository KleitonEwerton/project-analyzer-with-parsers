package com.minerprojects.data;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.client.RestTemplate;

import com.minerprojects.RefactoringReporter;

public class DataRefactoring {

    private static Logger logger = Logger.getLogger(DataRefactoring.class.getName());

    private String projectName;

    private String hash;

    private String hashPackage;

    private String hashPackageClass;

    private String type;

    public DataRefactoring(RefactoringReporter reporter) {

        RestTemplate restTemplate = new RestTemplate();

        this.projectName = reporter.getProjectName();
        this.hash = reporter.getHash();
        this.hashPackage = reporter.getHashPackage();
        this.hashPackageClass = reporter.getHashPackageClass();
        this.type = reporter.getType();

        try {

            restTemplate.postForObject("http://localhost:8080/api/refactorings", this, DataRefactoring.class);

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Erro ao enviar dados para a API: " + e.getMessage(), e);
        }

    }

    public DataRefactoring() {

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
