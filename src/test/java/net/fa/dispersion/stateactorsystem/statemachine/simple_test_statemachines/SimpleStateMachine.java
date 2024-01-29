package net.fa.dispersion.stateactorsystem.statemachine.simple_test_statemachines;

import lombok.extern.slf4j.Slf4j;
import net.fa.dispersion.stateactorsystem.statemachine.StateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.action.Action;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContextFactory;
import net.fa.dispersion.stateactorsystem.statemachine.state.State;
import net.fa.dispersion.stateactorsystem.statemachine.state.StateMachineState;
import net.fa.dispersion.stateactorsystem.statemachine.state.StateMachineStateConfiguration;
import net.fa.dispersion.stateactorsystem.statemachine.transition.StateMachineTransition;
import net.fa.dispersion.stateactorsystem.statemachine.transition.Transition;

import java.util.EnumSet;

import static net.fa.dispersion.stateactorsystem.statemachine.transition.Transition.Become;

@Slf4j
public class SimpleStateMachine {

    public static final StateMachine<SimpleStateMachineContext, Integer> stateMachine =
            new StateMachine.StateMachineBuilder<SimpleStateMachineContext, Integer>()
                    .setStateMachineContextFactory(new SimpleStateMachineContext())
                    .initialState(States.STATE_ONE)
                    .states(EnumSet.allOf(SimpleStateMachine.States.class))
                    .returnFunction(SimpleStateMachine::returnFunction)
                    .build();
    private static final Action<SimpleStateMachineContext> action = (context) -> {
//        log.info("SM Action log");
        context.num++;
//        logger.info(String.valueOf(context.num));
        return context;
    };

    private static final Action<SimpleStateMachineContext> failAction = (_) -> {
        log.info("SM Fail log");
        throw new RuntimeException("SM Fail");
    };

    private SimpleStateMachine() {
        // no op, static state-machine config
    }

    private static Integer returnFunction(SimpleStateMachineContext stateMachineContext) {
//        log.info("return function running");
        return stateMachineContext.num;
    }

    private static Transition<SimpleStateMachineContext> conditionalTransition(SimpleStateMachineContext stateMachineContext) {
        log.trace("conditional transition running");
        return Become(States.STATE_THREE);
    }

    private enum States implements StateMachineStateConfiguration<SimpleStateMachineContext> {
        STATE_ONE {
            public State<SimpleStateMachineContext> state() {
                return new StateMachineState.StateMachineStateBuilder<SimpleStateMachineContext>()
                        .setAction(action)
                        .build();
            }

            public StateMachineTransition<SimpleStateMachineContext> transition() {
                return StateMachineTransition.transition().nextState(STATE_TWO);
            }
        },

        STATE_TWO {
            public State<SimpleStateMachineContext> state() {
                return new StateMachineState.StateMachineStateBuilder<SimpleStateMachineContext>()
                        .setAction(action)
                        .build();
            }

            public StateMachineTransition<SimpleStateMachineContext> transition() {
                return StateMachineTransition
                        .conditionalTransition()
                        .condition(SimpleStateMachine::conditionalTransition);
            }
        },

        STATE_THREE {
            public State<SimpleStateMachineContext> state() {
                return new StateMachineState.StateMachineStateBuilder<SimpleStateMachineContext>()
                        .setAction(action)
                        .build();
            }

            public StateMachineTransition<SimpleStateMachineContext> transition() {
                return StateMachineTransition.end();
            }
        }
    }

    public static class SimpleStateMachineContext implements StateMachineContext<SimpleStateMachineContext>,
            StateMachineContextFactory<SimpleStateMachineContext> {
        public String string = "Simple Contextual String";
        public int num = 0;

        @Override
        public SimpleStateMachineContext newInstance() {
            return new SimpleStateMachineContext();
        }
    }
}
