package com.mycompany.testerefactoringminer2.version;

import java.util.Set;
import java.util.List;

import java.nio.file.Path;

import java.util.HashSet;
import java.util.Optional;

public class Commit {

    public static Set<Commit> commits = new HashSet<>();

    private String hash;
    private Set<String> parentsHash;

    private Commit parent;

    private Set<Path> filesPath;

    public Commit(String hash, Set<String> parentsHash) {
        this.hash = hash;
        this.parentsHash = parentsHash;

        commits.add(this);
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getParentHash() {
        return parentsHash.stream().findFirst().orElse(null);
    }

    public int getSizeParents() {
        return this.parentsHash.size();
    }

    public void setParentsHash(Set<String> parentsHash) {
        this.parentsHash = parentsHash;
    }

    public static Optional<Commit> getCommitByHash(String commitHash) {

        return Optional.ofNullable(Commit.commits.stream()
                .filter(commit -> commit.hash.equals(commitHash))
                .findFirst()
                .orElse(null));
    }

    /**
     * @return Set<String> return the parentsHash
     */
    public Set<String> getParentsHash() {
        return parentsHash;
    }

    /**
     * @return Set<Path> return the filesPath
     */
    public Set<Path> getFilesPath() {
        return filesPath;
    }

    /**
     * @param filesPath the filesPath to set
     */
    public void setFilesPath(Set<Path> filesPath) {
        this.filesPath = filesPath;
    }

    public Commit getParent() {
        return this.parent;
    }

    public void setParent(Commit parent) {
        this.parent = parent;
    }

}
