package com.github.f442y.dispersion.samples.TestStateMachineSpring;

import com.github.f442y.dispersion.core.application.ApplicationConfig;
import com.github.f442y.dispersion.core.monitor.StateMachineEventLogMonitor;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContextFactory;
import com.github.f442y.dispersion.core.statemachine.state.StateKey;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.InputFunction;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.OutputFunction;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMachineConfiguration;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMapBuilder;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.states.stateOne.StateOne;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.states.stateThree.StateThree;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.states.stateTwo.StateTwo;
import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Named
@Singleton
@Slf4j
public final class TestSpringStateMachine
        extends StateMachineConfiguration<TestSpringStateMachine.StateMachineSpringContext,
        TestSpringStateMachine.States, Integer, Integer> {
    public static final StateMachineSpringContext CONTEXT_FACTORY = new StateMachineSpringContext();

    @Inject
    public TestSpringStateMachine(StateOne stateOne, StateTwo stateTwo, StateThree stateThree,
                                  ApplicationConfig applicationConfig,
                                  StateMachineEventLogMonitor stateMachineEventLogMonitor
    ) {
        super(
                applicationConfig,
                stateMachineEventLogMonitor,
                StateMapBuilder
                        .<StateMachineSpringContext, States>buildStateMap(applicationConfig.skipStateMapBootValidation())
                        .states(States.class)
                        .addStateMapping(States.ONE, stateOne)
                        .addStateMapping(States.TWO, stateTwo)
                        .addStateMapping(States.THREE, stateThree)
                        .done()
                        .initialState(States.ONE)
                        .endState(States.END)
                        .build()
        );
    }

    @Override
    public @Nonnull StateMachineContextFactory<StateMachineSpringContext> stateMachineContextFactory() {
        return TestSpringStateMachine.CONTEXT_FACTORY;
    }

    @Override
    public InputFunction<StateMachineSpringContext, Integer> inputFunction() {
        return (sampleStateMachineContext, input) -> {
            sampleStateMachineContext.num = input;
//            log.info("input function | input: {}", input);
//            log.info("input function | context num: {}", sampleStateMachineContext.num);
            return sampleStateMachineContext;
        };
    }

    @Override
    public OutputFunction<StateMachineSpringContext, Integer> outputFunction() {
        return (sampleStateMachineContext) -> {
//            log.info("output function | context num: {}", sampleStateMachineContext.num);
            return sampleStateMachineContext.num;
        };
    }

    public enum States implements StateKey {
        ONE, TWO, THREE, END
    }

    public final static class StateMachineSpringContext
            implements StateMachineContext, StateMachineContextFactory<StateMachineSpringContext> {
        public String string = "Simple Contextual String";
        public int num = 0;

        @Override
        public StateMachineSpringContext newInstance() {
//            log.info("new context created");
            return new StateMachineSpringContext();
        }
    }
}
