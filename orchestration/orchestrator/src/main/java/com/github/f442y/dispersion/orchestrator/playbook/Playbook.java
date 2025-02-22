package com.github.f442y.dispersion.orchestrator.playbook;

import com.github.f442y.dispersion.core.orchestration.PlaybookAPI;
import com.github.f442y.dispersion.orchestrator.model.PlayTask;
import com.github.f442y.dispersion.orchestrator.model.PlayTaskRepository;
import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityDependencyRepository;
import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityTypeRepository;
import com.github.f442y.dispersion.orchestrator.model.run.PlayTaskRunEntityEventRepository;
import com.github.f442y.dispersion.orchestrator.model.run.PlayTaskRunExpectedEntityRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Named
@Singleton
public class Playbook implements PlaybookAPI {
    private static final Logger log = LoggerFactory.getLogger(Playbook.class);
    private final PlayTaskRepository playTaskRepository;
    private final PlayTaskEntityDependencyRepository playTaskEntityDependencyRepository;
    private final PlayTaskEntityTypeRepository playTaskEntityTypeRepository;
    private final PlayTaskRunEntityEventRepository playTaskRunEntityEventRepository;
    private final PlayTaskRunExpectedEntityRepository playTaskRunExpectedEntityRepository;

    private final ConcurrentHashMap<String, PlayTask> playTaskMap = new ConcurrentHashMap<>() {};

    @Inject
    public Playbook(PlayTaskRepository playTaskRepository,
                    PlayTaskEntityDependencyRepository playTaskEntityDependencyRepository,
                    PlayTaskEntityTypeRepository playTaskEntityTypeRepository,
                    PlayTaskRunEntityEventRepository playTaskRunEntityEventRepository,
                    PlayTaskRunExpectedEntityRepository playTaskRunExpectedEntityRepository
    ) {
        this.playTaskRepository = playTaskRepository;
        this.playTaskEntityDependencyRepository = playTaskEntityDependencyRepository;
        this.playTaskEntityTypeRepository = playTaskEntityTypeRepository;
        this.playTaskRunEntityEventRepository = playTaskRunEntityEventRepository;
        this.playTaskRunExpectedEntityRepository = playTaskRunExpectedEntityRepository;
    }

    @Transactional(propagation = Propagation.NESTED)
    @Override
    public Collection<PlayTask> getAllPlayTaskIds() {
        return playTaskRepository.findAll();
    }


    @Transactional
    public void reloadAllPlayTasksWithDependencies() {
        getAllPlayTaskIds().forEach(playTask -> {
            PlayTask playTaskEnriched = fetchPlayTaskLazyData(playTask);
            if (playTaskEnriched != null) {
                playTaskMap.put(playTask.getId(), playTaskEnriched);
            }
        });
    }

    @Transactional(propagation = Propagation.NESTED)
    public PlayTask fetchPlayTaskLazyData(PlayTask playTaskWithoutEnrichment) throws EntityNotFoundException {
        Optional<PlayTask> playTaskEnriched = playTaskRepository.findByName(playTaskWithoutEnrichment.getName());
        if (playTaskEnriched.isPresent()) {
            return playTaskEnriched.get();
        } else {
            throw new EntityNotFoundException(playTaskWithoutEnrichment.getName());
        }
    }

    // generate expectations for playTasks
    public void generateExpectations(PlayTask playTask) {
        // create PlayTaskRun for each zone using provider
        // create PlayTaskRunExpectedEntity entries with PlayTaskRun ID
    }

    // scan events to fulfil expectations

    // dispatch plays with met expectations

    public Collection<PlayTask> getAllPlayTasks() {
        return playTaskMap.values();
    }

    public Optional<PlayTask> getPlayTaskById(String id) {
        return Optional.ofNullable(playTaskMap.get(id));
    }
}
