package com.projectanalyzer.repositories.commiterror;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectanalyzer.entities.commiterror.CommitError;

public interface CommitErrorRepository extends JpaRepository<CommitError, Long> {

    // findAll
    List<CommitError> findAll();

}
