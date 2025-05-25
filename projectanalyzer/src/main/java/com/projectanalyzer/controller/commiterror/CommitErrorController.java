package com.projectanalyzer.controller.commiterror;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectanalyzer.entities.commiterror.CommitError;
import com.projectanalyzer.repositories.commiterror.CommitErrorRepository;

@RestController
@RequestMapping("/api/commiterror")
public class CommitErrorController {

    @Autowired
    private CommitErrorRepository commitErroRepository;

    @GetMapping
    public List<CommitError> getAllCommitErroRepositorys() {
        return commitErroRepository.findAll();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<CommitError> getCommitErroRepositoryById(@PathVariable Long id) {
        return commitErroRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar novo
    @PostMapping
    public CommitError createCommitErroRepository(@RequestBody CommitError commitErro) {
        return commitErroRepository.save(commitErro);
    }

}