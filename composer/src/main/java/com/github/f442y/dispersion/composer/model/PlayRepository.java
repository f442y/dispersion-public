package com.github.f442y.dispersion.composer.model;

import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Repository;

@Repository
public interface PlayRepository extends BasicRepository<Play<?, ?, ?>, Long> {}