package com.projectanalyzer.repositories.pmd;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectanalyzer.entities.pmd.PMD;

public interface PMDRepository extends JpaRepository<PMD, Long> {

}
