package com.example.exellsior.controller;

import com.example.exellsior.entity.VehicleType;
import com.example.exellsior.services.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle-types")
public class VehicleTypeController {
    @Autowired
    private VehicleTypeService service;

    @GetMapping
    public List<VehicleType> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public VehicleType getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public VehicleType create(@RequestBody VehicleType vehicleType) {
        return service.create(vehicleType);
    }

    @PutMapping("/{id}")
    public VehicleType update(@PathVariable Long id, @RequestBody VehicleType vehicleType) {
        return service.update(id, vehicleType);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
