package com.projectanalyzer.repositories.commit;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectanalyzer.entities.commit.Commit;

public interface CommitRepository extends JpaRepository<Commit, Long> {

}
