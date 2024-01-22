package com.github.f442y.dispersion.composer.model2;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayTaskRepository extends JpaRepository<PlayTask<?, ?>, String> {}
