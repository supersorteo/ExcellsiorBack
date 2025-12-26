package com.example.exellsior.controller;

import com.example.exellsior.entity.Space;
import com.example.exellsior.services.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping
    public List<Space> getAll() {
        return spaceService.getAllSpaces();
    }

    @GetMapping("/{key}")
    public Space getByKey(@PathVariable String key) {
        return spaceService.getByKey(key);
    }

    @PostMapping
    public Space create(@RequestBody Space space) {
        return spaceService.saveSpace(space);
    }





    @PutMapping("/{key}")
    public Space update(@PathVariable String key, @RequestBody Space updatedSpace) {
        return spaceService.updateSpace(key, updatedSpace);
    }



    @DeleteMapping("/{key}")
    public ResponseEntity<Void> delete(@PathVariable String key) {
        spaceService.deleteSpace(key);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{key}/transfer")
    public ResponseEntity<Space> transferSpace(
            @PathVariable String key,
            @RequestBody Map<String, String> body
    ) {
        String newSubsueloId = body.get("newSubsueloId");
        if (newSubsueloId == null || newSubsueloId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Space updated = spaceService.transferSpace(key, newSubsueloId);
        return ResponseEntity.ok(updated);
    }

}
