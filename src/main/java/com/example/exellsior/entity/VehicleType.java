package com.example.exellsior.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle_types")
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String model;

    @Column(nullable = false)
    private String category; // SUV, AUTO, PICKUP, ALTO PORTE, MOTO

    @Column(nullable = false)
    private Integer price;

    public VehicleType() {}

    public VehicleType(Long id, String model, String category, Integer price) {
        this.id = id;
        this.model = model;
        this.category = category;
        this.price = price;
    }

    public VehicleType(String model, String category, int price) {
        this.model = model;
        this.category = category;
        this.price = price;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
