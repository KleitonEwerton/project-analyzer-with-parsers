package com.mycompany.testerefactoringminer2;

import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

public class Commit {

    public static Set<Commit> commits = new HashSet<>();

    private String hash;
    private Set<String> parentsHash;

    public Commit(String hash, Set<String> parentsHash) {
        this.hash = hash;
        this.parentsHash = parentsHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getParentsHash() {
        return parentsHash.toString();
    }

    public void setParentsHash(Set<String> parentsHash) {
        this.parentsHash = parentsHash;
    }

    public static Optional<String> getFirstParentByHash(String commitHash) {
        return Commit.commits.stream()
                .filter(commit -> commit.hash.equals(commitHash))
                .findFirst()
                .flatMap(commit -> commit.parentsHash.stream().findFirst());
    }

}
