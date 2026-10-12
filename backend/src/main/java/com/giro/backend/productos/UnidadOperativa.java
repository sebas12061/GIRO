package com.giro.backend.productos;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "unidades_operativas")
public class UnidadOperativa {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(nullable = false, unique = true, length = 40) private String name;
    @Column(nullable = false, length = 10) private String abbreviation;
    protected UnidadOperativa() { }
    public UnidadOperativa(String name, String abbreviation) { this.name = name; this.abbreviation = abbreviation; }
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getAbbreviation() { return abbreviation; }
}
