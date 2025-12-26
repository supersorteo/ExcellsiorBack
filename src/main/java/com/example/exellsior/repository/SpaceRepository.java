package com.example.exellsior.repository;

import com.example.exellsior.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<Space, String> {
    void deleteBySubsueloId(String subsueloId);
    boolean existsBySubsueloIdAndOccupiedTrue(String subsueloId);
    boolean existsByKeyAndSubsueloId(String key, String subsueloId);

    boolean existsByDisplayNameAndSubsueloId(String displayName, String subsueloId);
}
