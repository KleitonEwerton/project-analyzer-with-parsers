package com.projectanalyzer.entity.commits;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "parents_hash", nullable = true)
    private Set<String> parentsHash;

    @Column(name = "develop", nullable = false)
    private String develop;

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

    /**
     * @return String return the develop
     */
    public String getDevelop() {
        return develop;
    }

    /**
     * @param develop the develop to set
     */
    public void setDevelop(String develop) {
        this.develop = develop;
    }

}
