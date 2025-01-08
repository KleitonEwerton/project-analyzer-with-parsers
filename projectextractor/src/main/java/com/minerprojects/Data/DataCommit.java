package com.minerprojects.data;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.client.RestTemplate;

import com.minerprojects.CommentReporter;
import com.minerprojects.CommitError;

public class DataCommit {

    private static final Logger logger = Logger.getLogger(CommentReporter.class.getName());

    private String projectName;

    private String hash;

    private List<String> parentsHash;

    public DataCommit(String projectName, String hash, List<String> parentsHash) {
        RestTemplate restTemplate = new RestTemplate();

        this.hash = hash;
        this.projectName = projectName;
        this.parentsHash = parentsHash;

        try {

            restTemplate.postForObject("http://localhost:8080/api/commit", this, DataCommit.class);

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Erro ao enviar dados para a API: " + e.getMessage(), e);
            new CommitError(projectName,
                    this.hash,
                    "Erro ao enviar dados para a api. " + DataCommit.class.getName());
        }
    }

    public DataCommit() {

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
     * @return List<String> return the parentsHash
     */
    public List<String> getParentsHash() {
        return parentsHash;
    }

    /**
     * @param parentsHash the parentsHash to set
     */
    public void setParentsHash(List<String> parentsHash) {
        this.parentsHash = parentsHash;
    }

}
