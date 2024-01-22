package net.fa.dispersion.stateactorsystem.statemachine.simple_test_statemachines;

import lombok.extern.slf4j.Slf4j;
import net.fa.dispersion.stateactorsystem.statemachine.StateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.StateMachineConfiguration;
import net.fa.dispersion.stateactorsystem.statemachine.action.NestedTaskScopeAction;
import net.fa.dispersion.stateactorsystem.statemachine.action.ProgressingAction;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContextFactory;
import net.fa.dispersion.stateactorsystem.statemachine.state.StateMachineState;
import net.fa.dispersion.stateactorsystem.statemachine.state.StateMachineStateConfiguration;
import net.fa.dispersion.stateactorsystem.statemachine.transition.StateMachineTransition;
import net.fa.dispersion.stateactorsystem.statemachine.transition.Transition;

import java.util.EnumSet;

import static net.fa.dispersion.stateactorsystem.statemachine.transition.Transition.Become;

@Slf4j
public final class SimpleParentStateMachine extends StateMachineConfiguration {
    public static final ScopedValue<SimpleParentMachineContext> CONTEXT = ScopedValue.newInstance();
    public static final StateMachine<SimpleParentMachineContext, Void> stateMachine =
            new StateMachine.StateMachineBuilder<SimpleParentMachineContext, Void>()
                    .setStateMachineContextFactory(new SimpleParentMachineContext())
                    .initialState(States.STATE_ONE)
                    .states(EnumSet.allOf(States.class))
                    .build();

    private static final ProgressingAction<SimpleParentMachineContext> action = (context) -> {
        log.trace(context.string);
        return context;
    };

    private static final NestedTaskScopeAction<SimpleParentMachineContext,
            SimpleNestedStateMachine.SimpleNestedMachineContext>
            nestedSMActionMid =
            new NestedTaskScopeAction.Builder<SimpleParentMachineContext,
                    SimpleNestedStateMachine.SimpleNestedMachineContext>()
                    .name("nested-mid")
                    .addScopedParentContext(CONTEXT)
                    .addStateMachine(SimpleNestedStateMachine.stateMachine)
                    .addStateMachine(SimpleNestedStateMachine.stateMachine)
                    .build();

    private SimpleParentStateMachine() {
        // no op, static state-machine config
    }

    private static Transition<SimpleParentMachineContext> transitionFn(SimpleParentMachineContext stateMachineContext) {
        return Become(States.STATE_THREE);
    }

    private enum States implements StateMachineStateConfiguration<SimpleParentMachineContext> {
        STATE_ONE {
            public StateMachineState<SimpleParentMachineContext> state() {
                return new StateMachineState.StateMachineStateBuilder<SimpleParentMachineContext>()
                        .setAction(action)
                        .build();
            }

            public StateMachineTransition<SimpleParentMachineContext> transition() {
                return StateMachineTransition.transition().nextState(STATE_TWO);
            }
        },

        STATE_TWO {
            public StateMachineState<SimpleParentMachineContext> state() {
                return new StateMachineState.StateMachineStateBuilder<SimpleParentMachineContext>()
                        .setAction(nestedSMActionMid)
                        .build();
            }

            public StateMachineTransition<SimpleParentMachineContext> transition() {
                return StateMachineTransition.conditionalTransition().condition(SimpleParentStateMachine::transitionFn);
            }
        },


        STATE_THREE {
            public StateMachineState<SimpleParentMachineContext> state() {
                return new StateMachineState.StateMachineStateBuilder<SimpleParentMachineContext>()
                        .setAction(Actions.action)
                        .build();
            }

            public StateMachineTransition<SimpleParentMachineContext> transition() {
                return StateMachineTransition.end();
            }
        }
    }

    public static class SimpleParentMachineContext implements StateMachineContext<SimpleParentMachineContext>,
            StateMachineContextFactory<SimpleParentMachineContext> {
        public String string = "SM contextual string [Parent]";
        public int num = 0;

        @Override
        public SimpleParentMachineContext newInstance() {
            return new SimpleParentMachineContext();
        }
    }
}
