package com.minerprojects;

import com.minerprojects.data.DataCommit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CommitReporter {

    public static List<CommitReporter> commits = new ArrayList();

    private String projectName;
    private String hash;
    private List<String> parentsHash;
    private Map<String, String> filesMAD = new HashMap();
    private List<String> javaFiles = new ArrayList<>();

    public CommitReporter(String projectName, String hash, List<String> parentsHash, Map<String, String> filesMAD) {
        this.projectName = projectName;
        this.hash = hash;
        this.parentsHash = parentsHash;
        this.filesMAD = filesMAD;

        commits.add(this);

        new DataCommit(projectName, hash, parentsHash);

    }

    public String getHash() {
        return hash;
    }

    public void setJavaFiles(List<String> javaFiles) {
        this.javaFiles = javaFiles;
    }

    public List<String> getJavaFiles() {
        return javaFiles;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Map<String, String> getFilesMAD() {
        return filesMAD;
    }

    public void setParentHashes(List<String> parentsHash) {
        this.parentsHash = parentsHash;

    }

    public String getParentHash() {
        if (parentsHash.size() == 1) {

            return parentsHash.stream().findFirst().orElse(null);

        } else if (parentsHash.size() > 1) {
            // retorna todos os pais entre [ ] separador por |
            return parentsHash.toString().replace(", ", "|");
        }

        return null;

    }

    public List<String> getParentHashes() {
        return this.parentsHash;
    }

    public int getSizeParents() {
        return this.parentsHash.size();
    }

    public static Optional<CommitReporter> getCommitByHash(String commitHash) {

        return Optional.ofNullable(CommitReporter.commits.stream()
                .filter(commit -> commit.hash.equals(commitHash))
                .findFirst()
                .orElse(null));
    }

    /**
     * @return Set<String> return the parentsHash
     */
    public List<String> getParentsHash() {
        return parentsHash;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}
