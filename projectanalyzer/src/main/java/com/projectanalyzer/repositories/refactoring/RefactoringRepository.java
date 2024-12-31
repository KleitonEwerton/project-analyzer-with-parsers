package com.projectanalyzer.repositories.refactoring;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectanalyzer.entities.refactoring.Refactoring;

import java.util.List;

public interface RefactoringRepository extends JpaRepository<Refactoring, Long> {

    Refactoring findById(long id);

    List<Refactoring> findByProjectName(String projectName);

    List<Refactoring> findByType(String type);

    List<Refactoring> findByHashAndType(String hash, String type);
}