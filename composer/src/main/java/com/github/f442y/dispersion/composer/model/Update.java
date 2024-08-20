package com.github.f442y.dispersion.composer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "UPDATE")
@Data
@NoArgsConstructor
public class Update {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime time;
    private String entity;
    private String zone;
    private String play;
    private String status;
}
