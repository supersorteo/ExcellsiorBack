package com.example.exellsior.services;

import com.example.exellsior.entity.VehicleType;
import com.example.exellsior.repository.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleTypeService {
    @Autowired
    private VehicleTypeRepository repository;

    public List<VehicleType> getAll() {
        return repository.findAll();
    }

    public VehicleType getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de vehículo no encontrado"));
    }

    public VehicleType getByModel(String model) {
        return repository.findByModel(model)
                .orElseThrow(() -> new RuntimeException("Modelo no encontrado: " + model));
    }

    public VehicleType create(VehicleType vehicleType) {
        return repository.save(vehicleType);
    }

    public VehicleType update(Long id, VehicleType updated) {
        VehicleType existing = getById(id);
        existing.setModel(updated.getModel());
        existing.setCategory(updated.getCategory());
        existing.setPrice(updated.getPrice());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
