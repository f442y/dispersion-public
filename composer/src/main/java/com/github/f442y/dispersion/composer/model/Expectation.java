package com.github.f442y.dispersion.composer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "EXPECTATION")
@Data
public class Expectation {
    private final String entity;
    private final String zone;
    @ManyToOne(fetch = FetchType.LAZY)
    private final Play<?, ?, ?> play;
    private final String status;
    @Id
    @GeneratedValue
    private Long id;
}
