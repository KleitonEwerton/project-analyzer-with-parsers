package com.projectanalyzer.controller.refactoring;

import com.projectanalyzer.entities.refactoring.Refactoring;
import com.projectanalyzer.repositories.refactoring.RefactoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refactorings")
public class RefactoringController {

    @Autowired
    private RefactoringRepository refactoringRepository;

    @GetMapping
    public List<Refactoring> getAllRefactorings() {
        return refactoringRepository.findAll();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Refactoring> getRefactoringById(@PathVariable Long id) {
        return refactoringRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar novo
    @PostMapping
    public Refactoring createRefactoring(@RequestBody Refactoring refactoring) {
        return refactoringRepository.save(refactoring);
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Refactoring> updateRefactoring(@PathVariable Long id,
            @RequestBody Refactoring refactoringDetails) {
        return refactoringRepository.findById(id)
                .map(refactoring -> {
                    refactoring.setProjectName(refactoringDetails.getProjectName());
                    refactoring.setHash(refactoringDetails.getHash());
                    refactoring.setHashPackageClass(refactoringDetails.getHashPackageClass());
                    refactoring.setType(refactoringDetails.getType());
                    Refactoring updatedRefactoring = refactoringRepository.save(refactoring);
                    return ResponseEntity.ok(updatedRefactoring);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
