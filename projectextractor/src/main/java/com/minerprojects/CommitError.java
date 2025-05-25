package com.minerprojects;

import java.util.logging.Level;

import org.springframework.web.client.RestTemplate;

public class CommitError {

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(CommitError.class.getName());

    private String projectName;

    private String hash;

    private String erroMSG;

    public CommitError() {
    }

    public CommitError(String projectName, String hash, String erroMSG) {
        RestTemplate restTemplate = new RestTemplate();

        this.hash = hash;
        this.projectName = projectName;
        // MAX 250 characters
        this.erroMSG = erroMSG.substring(0, erroMSG.length() > 250 ? 250 : erroMSG.length());

        try {

            restTemplate.postForObject("http://localhost:8080/api/commiterror", this, CommitError.class);

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Erro ao enviar dados para a API: " + e.getMessage(), e);

        }

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
     * @return String return the erroMSG
     */
    public String getErroMSG() {
        return erroMSG;
    }

    /**
     * @param erroMSG the erroMSG to set
     */
    public void setErroMSG(String erroMSG) {
        this.erroMSG = erroMSG;
    }

}
