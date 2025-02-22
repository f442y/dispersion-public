package com.github.f442y.dispersion.orchestrator.playbook;

import com.github.f442y.dispersion.core.orchestration.OrchestrationContextAPI;
import com.github.f442y.dispersion.orchestration.spi.entities.EntitySPI;
import com.github.f442y.dispersion.orchestration.spi.entities.SampleOrchestrationEntity;
import com.github.f442y.dispersion.orchestrator.model.PlayTask;
import com.github.f442y.dispersion.orchestrator.model.PlayTaskRepository;
import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityDependency;
import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityDependencyRepository;
import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityType;
import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityTypeRepository;
import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityZone;
import com.github.f442y.dispersion.orchestrator.model.entity.PlayTaskEntityZoneRepository;
import com.github.f442y.dispersion.orchestrator.model.orchestrationEntities.SampleOrchestrationEntityRepository;
import com.github.f442y.dispersion.orchestrator.model.run.PlayTaskRun;
import com.github.f442y.dispersion.orchestrator.model.run.PlayTaskRunEntityEvent;
import com.github.f442y.dispersion.orchestrator.model.run.PlayTaskRunEntityEventRepository;
import com.github.f442y.dispersion.orchestrator.model.run.PlayTaskRunExpectedEntity;
import com.github.f442y.dispersion.orchestrator.model.run.PlayTaskRunExpectedEntityRepository;
import com.github.f442y.dispersion.orchestrator.model.run.PlayTaskRunRepository;
import com.github.f442y.dispersion.orchestrator.zones.EntityZones;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Named
@Singleton
public class SamplePlaybook {

    private static final Logger log = LoggerFactory.getLogger(SamplePlaybook.class);
    private final PlayTaskRepository playTaskRepository;
    private final PlayTaskEntityDependencyRepository playTaskEntityDependencyRepository;
    private final PlayTaskEntityTypeRepository playTaskEntityTypeRepository;
    private final PlayTaskEntityZoneRepository playTaskEntityZoneRepository;
    private final PlayTaskRunEntityEventRepository playTaskRunEntityEventRepository;
    private final SampleOrchestrationEntityRepository sampleOrchestrationEntityRepository;
    private final PlayTaskRunExpectedEntityRepository playTaskRunExpectedEntityRepository;
    private final PlayTaskRunRepository playTaskRunRepository;
    private final DependencyEventScanner dependencyEventScanner;

    private UUID uuid;

    @Inject
    public SamplePlaybook(PlayTaskRepository playTaskRepository,
                          PlayTaskEntityDependencyRepository playTaskEntityDependencyRepository,
                          PlayTaskEntityTypeRepository playTaskEntityTypeRepository,
                          PlayTaskEntityZoneRepository playTaskEntityZoneRepository,
                          PlayTaskRunEntityEventRepository playTaskRunEntityEventRepository,
                          SampleOrchestrationEntityRepository sampleOrchestrationEntityRepository,
                          PlayTaskRunExpectedEntityRepository playTaskRunExpectedEntityRepository,
                          PlayTaskRunRepository playTaskRunRepository, DependencyEventScanner dependencyEventScanner
    ) {
        this.playTaskRepository = playTaskRepository;
        this.playTaskEntityDependencyRepository = playTaskEntityDependencyRepository;
        this.playTaskEntityTypeRepository = playTaskEntityTypeRepository;
        this.playTaskEntityZoneRepository = playTaskEntityZoneRepository;
        this.playTaskRunEntityEventRepository = playTaskRunEntityEventRepository;
        this.sampleOrchestrationEntityRepository = sampleOrchestrationEntityRepository;
        this.playTaskRunExpectedEntityRepository = playTaskRunExpectedEntityRepository;
        this.playTaskRunRepository = playTaskRunRepository;
        this.dependencyEventScanner = dependencyEventScanner;
    }

    @Transactional
    public void init() {

        // Create PlayTasks
        PlayTask playTaskOne = new PlayTask("playTaskOne");
        PlayTask playTaskTwo = new PlayTask("playTaskTwo");
        PlayTask playTaskThree = new PlayTask("playTaskThree");
        Set<PlayTask> playTasks = Set.of(playTaskOne, playTaskTwo, playTaskThree);
        playTaskRepository.saveAllAndFlush(playTasks);

        // Create Entities
        com.github.f442y.dispersion.orchestrator.model.orchestrationEntities.SampleOrchestrationEntity
                sampleOrchestrationEntityOne =
                new com.github.f442y.dispersion.orchestrator.model.orchestrationEntities.SampleOrchestrationEntity(
                        "sampleOrchestrationEntityOne",
                        "info one"
                );
        com.github.f442y.dispersion.orchestrator.model.orchestrationEntities.SampleOrchestrationEntity
                sampleOrchestrationEntityTwo =
                new com.github.f442y.dispersion.orchestrator.model.orchestrationEntities.SampleOrchestrationEntity(
                        "sampleOrchestrationEntityTwo",
                        "info two"
                );
        com.github.f442y.dispersion.orchestrator.model.orchestrationEntities.SampleOrchestrationEntity
                sampleOrchestrationEntityThree =
                new com.github.f442y.dispersion.orchestrator.model.orchestrationEntities.SampleOrchestrationEntity(
                        "sampleOrchestrationEntityThree",
                        "info three"
                );
        sampleOrchestrationEntityRepository.saveAllAndFlush(Set.of(
                sampleOrchestrationEntityOne,
                sampleOrchestrationEntityTwo,
                sampleOrchestrationEntityThree
        ));

        // Build Entity Composition for each play
        PlayTaskEntityType playTaskEntityCompositionPlayOneSOEType =
                new PlayTaskEntityType("SOE_PT1", playTaskOne, EntitySPI.Sample_OE);
        PlayTaskEntityType playTaskEntityCompositionPlayTwoSOEType =
                new PlayTaskEntityType("SOE_PT2", playTaskTwo, EntitySPI.Sample_OE);
        PlayTaskEntityType playTaskEntityCompositionPlayThreeSOEType =
                new PlayTaskEntityType("SOE_PT3", playTaskThree, EntitySPI.Sample_OE);
        playTaskEntityTypeRepository.saveAllAndFlush(Set.of(
                playTaskEntityCompositionPlayOneSOEType,
                playTaskEntityCompositionPlayTwoSOEType,
                playTaskEntityCompositionPlayThreeSOEType
        ));

        // Build Zone Compostion for each play

        PlayTaskEntityZone ptezPOneTestZone = new PlayTaskEntityZone("TZ_PT1", playTaskOne, EntityZones.TestZone);
        PlayTaskEntityZone ptezPOneZone2 = new PlayTaskEntityZone("Z2_PT1", playTaskOne, EntityZones.Zone2);
        PlayTaskEntityZone ptezPTwoTestZone = new PlayTaskEntityZone("TZ_PT2", playTaskTwo, EntityZones.TestZone);
        PlayTaskEntityZone ptezPTwoZone2 = new PlayTaskEntityZone("Z2_PT2", playTaskTwo, EntityZones.Zone2);
        PlayTaskEntityZone ptezPThreeTestZone = new PlayTaskEntityZone("TZ_PT3", playTaskThree, EntityZones.TestZone);
        PlayTaskEntityZone ptezPThreeZone2 = new PlayTaskEntityZone("Z2_PT3", playTaskThree, EntityZones.Zone2);
        playTaskEntityZoneRepository.saveAllAndFlush(Set.of(
                ptezPOneTestZone,
                ptezPOneZone2,
                ptezPTwoTestZone,
                ptezPTwoZone2,
                ptezPThreeTestZone,
                ptezPThreeZone2
        ));

        // Create Entity specific dependencies between PlayTasks
        PlayTaskEntityDependency playTaskDependencyTwoOne = new PlayTaskEntityDependency(
                "playTaskDependencyTwoOne",
                playTaskTwo,
                EntitySPI.Sample_OE,
                EntityZones.TestZone,
                playTaskOne,
                EntitySPI.Sample_OE,
                EntityZones.TestZone
        );
        PlayTaskEntityDependency playTaskDependencyThreeTwo = new PlayTaskEntityDependency(
                "playTaskDependencyThreeTwo",
                playTaskThree,
                EntitySPI.Sample_OE,
                EntityZones.TestZone,
                playTaskTwo,
                EntitySPI.Sample_OE,
                EntityZones.TestZone
        );
        PlayTaskEntityDependency playTaskDependencyThreeOne = new PlayTaskEntityDependency(
                "playTaskDependencyThreeOne",
                playTaskThree,
                EntitySPI.Sample_OE,
                EntityZones.TestZone,
                playTaskOne,
                EntitySPI.Sample_OE,
                EntityZones.TestZone
        );
        Set<PlayTaskEntityDependency> playTaskDependencies =
                Set.of(playTaskDependencyTwoOne, playTaskDependencyThreeTwo, playTaskDependencyThreeOne);

        playTaskEntityDependencyRepository.saveAllAndFlush(playTaskDependencies);

        // Create Runs

        PlayTaskRun playTaskOneRun = new PlayTaskRun(playTaskOne, EntityZones.TestZone);
        PlayTaskRun playTaskTwoRun = new PlayTaskRun(playTaskTwo, EntityZones.TestZone);
        PlayTaskRun playTaskThreeRun = new PlayTaskRun(playTaskThree, EntityZones.TestZone);

        playTaskRunRepository.saveAllAndFlush(Set.of(playTaskOneRun, playTaskTwoRun, playTaskThreeRun));
        this.uuid = playTaskTwoRun.getId();


        // Create Entity Expectations
        PlayTaskRunExpectedEntity playTaskEntityExpectationSOEOnePTOne = new PlayTaskRunExpectedEntity(
                playTaskOneRun,
                sampleOrchestrationEntityOne,
                PlayTaskRunExpectedEntity.ExpectationStatus.DEPENDENCIES_PENDING
        );
        PlayTaskRunExpectedEntity playTaskEntityExpectationSOETwoPTOne = new PlayTaskRunExpectedEntity(
                playTaskOneRun,
                sampleOrchestrationEntityTwo,
                PlayTaskRunExpectedEntity.ExpectationStatus.DEPENDENCIES_PENDING
        );
        PlayTaskRunExpectedEntity playTaskEntityExpectationSOEThreePTOne = new PlayTaskRunExpectedEntity(
                playTaskOneRun,
                sampleOrchestrationEntityThree,
                PlayTaskRunExpectedEntity.ExpectationStatus.DEPENDENCIES_PENDING
        );
        PlayTaskRunExpectedEntity playTaskEntityExpectationSOEOnePTTwo = new PlayTaskRunExpectedEntity(
                playTaskTwoRun,
                sampleOrchestrationEntityOne,
                PlayTaskRunExpectedEntity.ExpectationStatus.DEPENDENCIES_PENDING
        );
        PlayTaskRunExpectedEntity playTaskEntityExpectationSOETwoPTTwo = new PlayTaskRunExpectedEntity(
                playTaskTwoRun,
                sampleOrchestrationEntityTwo,
                PlayTaskRunExpectedEntity.ExpectationStatus.DEPENDENCIES_PENDING
        );
        PlayTaskRunExpectedEntity playTaskEntityExpectationSOEThreePTTwo = new PlayTaskRunExpectedEntity(
                playTaskTwoRun,
                sampleOrchestrationEntityThree,
                PlayTaskRunExpectedEntity.ExpectationStatus.DEPENDENCIES_PENDING
        );
        playTaskRunExpectedEntityRepository.saveAllAndFlush(Set.of(
                playTaskEntityExpectationSOEOnePTOne,
                playTaskEntityExpectationSOETwoPTOne,
                playTaskEntityExpectationSOEThreePTOne,
                playTaskEntityExpectationSOEOnePTTwo,
                playTaskEntityExpectationSOETwoPTTwo,
                playTaskEntityExpectationSOEThreePTTwo
        ));

        // Create Entity Events
        PlayTaskRunEntityEvent playTaskOneEntityEventSOEOne = new PlayTaskRunEntityEvent(
                "eventSOEOnePTOne",
                playTaskOne,
                playTaskOneRun,
                sampleOrchestrationEntityOne,
                PlayTaskRunEntityEvent.EventStatus.COMPLETE
        );
        PlayTaskRunEntityEvent playTaskOneEntityEventSOETwo = new PlayTaskRunEntityEvent(
                "eventSOETwoPTOne",
                playTaskOne,
                playTaskOneRun,
                sampleOrchestrationEntityTwo,
                PlayTaskRunEntityEvent.EventStatus.COMPLETE
        );
        PlayTaskRunEntityEvent playTaskOneEntityEventSOEThree = new PlayTaskRunEntityEvent(
                "eventSOEThreePTOne",
                playTaskOne,
                playTaskOneRun,
                sampleOrchestrationEntityThree,
                PlayTaskRunEntityEvent.EventStatus.COMPLETE
        );

        playTaskRunEntityEventRepository.saveAllAndFlush(Set.of(
                playTaskOneEntityEventSOEOne,
                playTaskOneEntityEventSOETwo,
                playTaskOneEntityEventSOEThree
        ));
    }

    @Transactional
    public void useData() {
        var playTask = playTaskRepository.findByName("playTaskOne");
        if (playTask.isPresent()) {
            OrchestrationContextAPI
                    .getContextPluginInterfaces(SampleOrchestrationEntity.SingleEntityContextAPI.class)
                    .forEach(i -> log.info(i.toString()));
        } else {
            log.error("play task not found");
        }

//        dependencyEventScanner.scanEvents(playTaskRunRepository.findById(uuid).get());

//        SOEProvider
//                .getPlayRunContexts()
//                .forEach(playRunContext -> log.info(playRunContext.contextSampleOrchestrationEntity().getId()));
//        OrchestrationContextAPI.getInterfaces(SOEProvider.getPlayRunContextAPI()).forEach(i -> log.info(i.toString
//        ()));
    }
}
