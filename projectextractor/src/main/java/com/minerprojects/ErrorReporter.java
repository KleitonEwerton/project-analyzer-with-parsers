package com.minerprojects;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.springframework.web.client.RestTemplate;

public class ErrorReporter {

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(ErrorReporter.class.getName());

    private String projectName;

    private String hash;

    private Set<String> parentsHash;

    private String erroMSG;

    public static List<ErrorReporter> errosCheckout = new ArrayList<>();

    public ErrorReporter(String projectName, String hash, Set<String> parentsHash, String erroMSG) {
        RestTemplate restTemplate = new RestTemplate();

        this.hash = hash;
        this.parentsHash = parentsHash;
        this.projectName = projectName;
        this.erroMSG = erroMSG;
        errosCheckout.add(this);

        try {
            restTemplate.postForObject("http://localhost:8080/api/commitErro", this, ErrorReporter.class);

            logger.info(
                    "Dados enviados com sucesso para a API.  - "
                            + this.projectName + " - " + this.erroMSG);

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

    /**
     * @return Set<String> return the parentsHash
     */
    public Set<String> getParentsHash() {
        return parentsHash;
    }

    /**
     * @param parentsHash the parentsHash to set
     */
    public void setParentsHash(Set<String> parentsHash) {
        this.parentsHash = parentsHash;
    }

}
