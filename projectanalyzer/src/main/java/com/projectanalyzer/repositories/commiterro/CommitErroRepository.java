package com.projectanalyzer.repositories.commiterro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectanalyzer.entities.commiterro.CommitErro;

public interface CommitErroRepository extends JpaRepository<CommitErro, Long> {

    // findAll
    List<CommitErro> findAll();

}
