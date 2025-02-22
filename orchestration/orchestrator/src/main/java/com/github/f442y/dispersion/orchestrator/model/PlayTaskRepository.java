package com.github.f442y.dispersion.orchestrator.model;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayTaskRepository extends JpaRepository<PlayTask, String> {

    @EntityGraph(attributePaths = {"entityComposition", "zoneComposition", "playTasksIDependOn",
            "playTasksDependingOnMe"})
    Optional<PlayTask> findByName(String name);
}
