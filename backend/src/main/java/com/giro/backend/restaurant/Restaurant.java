package com.giro.backend.restaurant;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String identification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RestaurantStatus status = RestaurantStatus.ACTIVE;

    protected Restaurant() {
    }

    public Restaurant(String name, String identification) {
        this.name = name;
        this.identification = identification;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIdentification() {
        return identification;
    }

    public RestaurantStatus getStatus() {
        return status;
    }
}
