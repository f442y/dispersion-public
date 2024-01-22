package com.github.f442y.dispersion.composer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXPECTATION")
public class Expectation {
    public Expectation() {}

    public Expectation(String entity, String zone, Play<?, ?, ?> play, String status) {
        this.entity = entity;
        this.zone = zone;
        this.play = play;
        this.status = status;
    }

    private String entity;
    private String zone;
    @ManyToOne(fetch = FetchType.LAZY)
    private Play<?, ?, ?> play;
    private String status;
    @Id
    @GeneratedValue
    private Long id;
}
