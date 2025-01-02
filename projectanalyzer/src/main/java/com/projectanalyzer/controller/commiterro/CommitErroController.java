package com.projectanalyzer.controller.commiterro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectanalyzer.entities.commiterro.CommitErro;
import com.projectanalyzer.repositories.commiterro.CommitErroRepository;

@RestController
@RequestMapping("/api/commitErro")
public class CommitErroController {

    @Autowired
    private CommitErroRepository commitErroRepository;

    @GetMapping
    public List<CommitErro> getAllCommitErroRepositorys() {
        return commitErroRepository.findAll();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<CommitErro> getCommitErroRepositoryById(@PathVariable Long id) {
        return commitErroRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar novo
    @PostMapping
    public CommitErro createCommitErroRepository(@RequestBody CommitErro commitErro) {
        return commitErroRepository.save(commitErro);
    }

}