package com.github.f442y.dispersion.orchestrator.model.run;

import com.github.f442y.dispersion.orchestrator.model.PlayTask;
import com.github.f442y.dispersion.orchestrator.zones.EntityZones;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayTaskRunRepository extends JpaRepository<PlayTaskRun, UUID> {
    @EntityGraph(attributePaths = {"entityExpectations", "entityEvents"})
    @NonNull
    PlayTaskRun getById(@NonNull UUID uuid);

    @EntityGraph(attributePaths = {"entityEvents"})
    Optional<Collection<PlayTaskRun>> findAllByPlayTaskAndEntityZone(PlayTask playTask, EntityZones entityZone);
}
