package com.projectanalyzer.repositories.refactoring;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectanalyzer.entities.refactoring.Refactoring;

public interface RefactoringRepository extends JpaRepository<Refactoring, Long> {

}
