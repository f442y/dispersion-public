package com.github.f442y.dispersion.composer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Table(name = "EXPECTATION")
@Data
@NoArgsConstructor
public class Expectation {
    @Id
    private Long id;
    private String entity;
    private String zone;
    private ArrayList<String> expectedPlays;
}
