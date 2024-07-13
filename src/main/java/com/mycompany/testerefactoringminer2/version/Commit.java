package com.mycompany.testerefactoringminer2.version;

import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

public class Commit {

    public static Set<Commit> commits = new HashSet<>();

    private String hash;
    private Set<String> parentsHash;

    private Commit parent;

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
        if (parentsHash.size() == 1) {
            return parentsHash.stream().findFirst().orElse(null);

        } else if (parentsHash.size() > 1) {
            // retorna todos os pais entre [ ] separador por |
            return parentsHash.toString().replace(", ", "|");
        }

        return null;

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

    public Commit getParent() {
        return this.parent;
    }

    public void setParent(Commit parent) {
        this.parent = parent;
    }

}
