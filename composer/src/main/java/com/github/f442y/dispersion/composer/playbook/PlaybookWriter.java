package com.github.f442y.dispersion.composer.playbook;

import com.github.f442y.dispersion.composer.model2.PlayTask;
import com.github.f442y.dispersion.composer.model2.PlayTaskDependency;
import com.github.f442y.dispersion.composer.model2.PlayTaskDependencyRepository;
import com.github.f442y.dispersion.composer.model2.PlayTaskRepository;
import com.github.f442y.dispersion.core.orchestration.OrchestrationEntityProvider;
import com.github.f442y.dispersion.service.interfaces.SimpleOrchestrationService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

@Named
@Singleton
public class PlaybookWriter {
    private static final Logger log = LoggerFactory.getLogger(PlaybookWriter.class);

    private final PlayTaskRepository playTaskRepository;
    private final PlayTaskDependencyRepository playTaskDependencyRepository;
    private final OrchestrationEntityProvider<SimpleOrchestrationEntity> provider;

    @Inject
    public PlaybookWriter(PlayTaskRepository playTaskRepository,
                          PlayTaskDependencyRepository playTaskDependencyRepository,
                          OrchestrationEntityProvider<SimpleOrchestrationEntity> provider
    ) {
        this.playTaskRepository = playTaskRepository;
        this.playTaskDependencyRepository = playTaskDependencyRepository;
        this.provider = provider;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startHook() {
        init();
    }

    @Transactional
    private void init() {
        PlayTask<SimpleOrchestrationEntity, SimpleOrchestrationService> playTaskOne =
                new PlayTask<>(provider, Collections.emptySet(), "playTaskOne");
        PlayTask<SimpleOrchestrationEntity, SimpleOrchestrationService> playTaskTwo =
                new PlayTask<>(provider, Collections.emptySet(), "playTaskTwo");
        PlayTask<SimpleOrchestrationEntity, SimpleOrchestrationService> playTaskThree =
                new PlayTask<>(provider, Collections.emptySet(), "playTaskThree");
        Set<PlayTask<?, ?>> playTasks = Set.of(playTaskOne, playTaskTwo, playTaskThree);

        playTaskRepository.saveAllAndFlush(playTasks);

        PlayTaskDependency playTaskDependencyTwoOne =
                new PlayTaskDependency("playTaskDependencyTwoOne", playTaskTwo, playTaskOne);
        PlayTaskDependency playTaskDependencyThreeTwo =
                new PlayTaskDependency("playTaskDependencyThreeTwo", playTaskThree, playTaskTwo);
        PlayTaskDependency playTaskDependencyThreeOne =
                new PlayTaskDependency("playTaskDependencyThreeOne", playTaskThree, playTaskOne);
        Set<PlayTaskDependency> playTaskDependencies =
                Set.of(playTaskDependencyTwoOne, playTaskDependencyThreeTwo, playTaskDependencyThreeOne);

        playTaskDependencyRepository.saveAllAndFlush(playTaskDependencies);

        playTaskRepository
                .findById(playTaskThree.getId())
                .get()
                .getPlayTasksIDependOn()
                .forEach(playTaskDependency -> log.info(
                        "PlayTask I ({}) depend on: {}",
                        playTaskThree.getName(),
                        playTaskDependency.getPlayTaskSource().getName()
                ));

        playTaskRepository
                .findById(playTaskTwo.getId())
                .get()
                .getPlayTasksDependingOnMe()
                .forEach(playTaskDependency -> log.info(
                        "PlayTasks that depend on me ({}): {}",
                        playTaskTwo.getName(),
                        playTaskDependency.getPlayTaskToRun().getName()
                ));
    }
}
