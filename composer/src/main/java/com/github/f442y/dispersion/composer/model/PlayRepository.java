package com.github.f442y.dispersion.composer.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayRepository extends JpaRepository<Play<?, ?, ?>, Long> {}