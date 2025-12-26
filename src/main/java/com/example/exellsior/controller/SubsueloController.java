package com.example.exellsior.controller;

import com.example.exellsior.entity.Subsuelo;
import com.example.exellsior.services.SubsueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subsuelos")
public class SubsueloController {
    @Autowired
    private SubsueloService subsueloService;

    @GetMapping
    public List<Subsuelo> getAll() {
        return subsueloService.getAllSubsuelos();
    }

    @GetMapping("/{id}")
    public Subsuelo getById(@PathVariable String id) {
        return subsueloService.getById(id);
    }

    @PostMapping
    public Subsuelo create(@RequestBody Subsuelo subsuelo) {
        return subsueloService.saveSubsuelo(subsuelo);
    }


    @PutMapping("/{id}")
    public Subsuelo update(@PathVariable String id, @RequestBody Subsuelo updatedSubsuelo) {
        updatedSubsuelo.setId(id); // Aseguramos el ID correcto
        return subsueloService.updateSubsuelo(id, updatedSubsuelo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        subsueloService.deleteSubsuelo(id);
        return ResponseEntity.noContent().build();
    }
}
