package com.github.f442y.dispersion.composer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "EXPECTATION_UPDATE")
@Data
public class ExpectationUpdate {
    private final LocalDateTime time;
    @ManyToOne(fetch = FetchType.LAZY)
    private final Expectation expectation;
    //    private final String playbookVersion;
    private final String status;
    @Id
    @GeneratedValue
    private Long id;

    public ExpectationUpdate(LocalDateTime time, Expectation expectation, String status) {
        this.time = time;
        this.status = status;
        this.expectation = expectation;
    }
}
