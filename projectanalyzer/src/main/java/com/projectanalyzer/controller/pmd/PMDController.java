package com.projectanalyzer.controller.pmd;

import com.projectanalyzer.entities.pmd.PMD;
import com.projectanalyzer.repositories.pmd.PMDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pmd")
public class PMDController {

    @Autowired
    private PMDRepository pmdRepository;

    @GetMapping
    public List<PMD> getAllPMDs() {
        return pmdRepository.findAll();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<PMD> getPMDById(@PathVariable Long id) {
        return pmdRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar novo
    @PostMapping
    public PMD createPMD(@RequestBody PMD pmd) {
        return pmdRepository.save(pmd);
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<PMD> updatePMD(@PathVariable Long id, @RequestBody PMD pmdDetails) {
        return pmdRepository.findById(id)
                .map(pmd -> {
                    pmd.setProjectName(pmdDetails.getProjectName());
                    pmd.setHash(pmdDetails.getHash());
                    pmd.setHashPackageClass(pmdDetails.getHashPackageClass());
                    pmd.setType(pmdDetails.getType());
                    pmd.setBeginLine(pmdDetails.getBeginLine());
                    pmd.setEndLine(pmdDetails.getEndLine());
                    pmd.setBeginColumn(pmdDetails.getBeginColumn());
                    pmd.setPriority(pmdDetails.getPriority());
                    pmd.setParentHash(pmdDetails.getParentHash());
                    PMD updatedPMD = pmdRepository.save(pmd);
                    return ResponseEntity.ok(updatedPMD);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}