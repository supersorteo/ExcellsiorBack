package com.example.exellsior.repository;

import com.example.exellsior.entity.Subsuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubsueloRepository extends JpaRepository<Subsuelo, String> {
}
