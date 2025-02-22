package com.github.f442y.dispersion.orchestrator.playbook;

import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityDependency;
import com.github.f442y.dispersion.orchestrator.model.run.PlayTaskRun;
import com.github.f442y.dispersion.orchestrator.model.run.PlayTaskRunEntityEventRepository;
import com.github.f442y.dispersion.orchestrator.model.run.PlayTaskRunRepository;
import com.github.f442y.dispersion.orchestrator.zones.EntityZones;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Named
public class DependencyEventScanner {
    private static final Logger log = LoggerFactory.getLogger(DependencyEventScanner.class);
    private final PlayTaskRunEntityEventRepository playTaskRunEntityEventRepository;
    private final PlayTaskRunRepository playTaskRunRepository;

    @Inject
    public DependencyEventScanner(PlayTaskRunEntityEventRepository playTaskRunEntityEventRepository,
                                  PlayTaskRunRepository playTaskRunRepository
    ) {
        this.playTaskRunEntityEventRepository = playTaskRunEntityEventRepository;
        this.playTaskRunRepository = playTaskRunRepository;
    }

    @Transactional
    public void validateDependencyExpectations(PlayTaskRun playTaskRun) {
        var expectations = playTaskRun.getEntityExpectations();
        expectations.forEach(expectation -> log.info(expectation.getOrchestrationEntityId()));
        EntityZones playTaskRunZone = playTaskRun.getEntityZone();
        // get dependencies for this run in the same zone
        var playTaskEntityDependencies = playTaskRun
                .getPlayTask()
                .getPlayTasksIDependOn()
                .stream()
                .filter(playTaskEntityDependency -> playTaskEntityDependency
                        .getRunEntityZone()
                        .equals(playTaskRunZone))
                .collect(Collectors.toSet());

    }

    @Transactional
    public void scanEvents(PlayTaskRun playTaskRun) {
        log.info("scanning");
        var expectations = playTaskRun.getEntityExpectations();
        expectations.forEach(expectation -> log.info(expectation.getOrchestrationEntityId()));
        EntityZones playTaskRunZone = playTaskRun.getEntityZone();
        // get dependencies for this run in the same zone
        var playTaskEntityDependencies = playTaskRun
                .getPlayTask()
                .getPlayTasksIDependOn()
                .stream()
                .filter(playTaskEntityDependency -> playTaskEntityDependency
                        .getRunEntityZone()
                        .equals(playTaskRunZone))
                .collect(Collectors.toSet());
        for (PlayTaskEntityDependency playTaskEntityDependency : playTaskEntityDependencies) {
            var prevRuns =
                    playTaskRunRepository.findAllByPlayTaskAndEntityZone(
                            playTaskEntityDependency.getPlayTaskSource(),
                            playTaskEntityDependency.getSourceEntityZone()
                    );
            if (prevRuns.isEmpty() || prevRuns.get().isEmpty()) {
                log.info("no prev runs found");
                return;
            }
            Collection<PlayTaskRun> playTaskRuns = prevRuns.get();
            playTaskRuns.forEach(run -> {
                run.getEntityEvents().forEach(playTaskRunEntityEvent -> {
                    log.info(playTaskRunEntityEvent.getOrchestrationEntityId());
                });
            });
        }
    }
}
