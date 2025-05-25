package com.projectanalyzer.controller.commit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projectanalyzer.entities.commit.Commit;
import com.projectanalyzer.repositories.commit.CommitRepository;

@RestController
@RequestMapping("/api/commit")
public class CommitController {

    @Autowired
    private CommitRepository commitRepository;

    @GetMapping
    public List<Commit> getAllCommitRepositorys() {
        return commitRepository.findAll();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Commit> getCommitRepositoryById(@PathVariable Long id) {
        return commitRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar novo
    @PostMapping
    public Commit createCommitRepository(@RequestBody Commit commitErro) {
        return commitRepository.save(commitErro);
    }

}