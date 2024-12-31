package com.projectanalyzer.repositories.pmd;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectanalyzer.entities.pmd.PMD;

public interface PMDRepository extends JpaRepository<PMD, Long> {
    List<PMD> findByProjectName(String projectName);

    List<PMD> findByType(String type);

    List<PMD> findByHashAndType(String hash, String type);
}
