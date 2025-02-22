package com.github.f442y.dispersion.orchestrator.state.webapi.model;

import com.github.f442y.dispersion.orchestrator.model.PlayTask;
import com.github.f442y.dispersion.orchestrator.playbook.Playbook;
import io.avaje.jsonb.Json;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Named
@Singleton
public class PlayTaskViewer {
    private final Playbook playbook;

    @Inject
    public PlayTaskViewer(Playbook playbook) {
        this.playbook = playbook;
    }

    @Transactional
    public PlayTasksView getAllPlayTasks() {
        Collection<PlayTask> playTasks = playbook.getAllPlayTasks();
        if (playTasks.isEmpty()) {
            return null;
        }
        return new PlayTasksView(
                playTasks.size(),
                playTasks
                        .stream()
                        .map(playTask -> new PlayTaskIdView(playTask.getId(), playTask.getName()))
                        .collect(Collectors.toSet())
        );
    }

    @Transactional
    public PlayTaskView getPlayTask(String playTaskId) {
        PlayTask playTask = playbook.getPlayTaskById(playTaskId).orElse(null);
        if (playTask == null) {
            return null;
        }
        return new PlayTaskView(
                new PlayTaskIdView(playTask.getId(), playTask.getName()),
                playTask
                        .getEntityComposition()
                        .stream()
                        .map(EntityCompositionViewer::convertToView)
                        .collect(Collectors.toSet()),
                playTask
                        .getZoneComposition()
                        .stream()
                        .map(ZoneCompositionViewer::convertToView)
                        .collect(Collectors.toSet()),
                playTask
                        .getPlayTasksIDependOn()
                        .stream()
                        .map(EntityDependencyViewer::convertToSourceView)
                        .collect(Collectors.toSet()),
                playTask
                        .getPlayTasksDependingOnMe()
                        .stream()
                        .map(EntityDependencyViewer::convertToForwardView)
                        .collect(Collectors.toSet())
        );
    }

    @Json
    public record PlayTaskIdView(String id, String name) {}

    @Json
    public record PlayTasksView(Integer count, Set<PlayTaskIdView> playTaskIds) {}

    @Json
    public record PlayTaskView(PlayTaskIdView playTaskId,
                               Set<EntityCompositionViewer.EntityCompositionView> entityComposition,
                               Set<ZoneCompositionViewer.ZoneCompositionView> zoneComposition,
                               Set<EntityDependencyViewer.SourceDependencyView> playTasksIDependOn,
                               Set<EntityDependencyViewer.ForwardDependencyView> playTasksDependingOnMe) {}
}
