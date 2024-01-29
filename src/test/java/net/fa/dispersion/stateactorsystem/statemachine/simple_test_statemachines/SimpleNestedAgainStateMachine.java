package net.fa.dispersion.stateactorsystem.statemachine.simple_test_statemachines;

import lombok.extern.slf4j.Slf4j;
import net.fa.dispersion.stateactorsystem.statemachine.NestedStateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.action.Action;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContextFactory;
import net.fa.dispersion.stateactorsystem.statemachine.state.State;
import net.fa.dispersion.stateactorsystem.statemachine.state.StateMachineState;
import net.fa.dispersion.stateactorsystem.statemachine.state.StateMachineStateConfiguration;
import net.fa.dispersion.stateactorsystem.statemachine.transition.StateMachineTransition;

import java.util.EnumSet;

@Slf4j
public class SimpleNestedAgainStateMachine {
    public static final ScopedValue<SimpleNestedAgainMachineContext> CONTEXT = ScopedValue.newInstance();
    public static final NestedStateMachine<SimpleNestedAgainMachineContext,
            SimpleNestedStateMachine.SimpleNestedMachineContext>
            stateMachine =
            new NestedStateMachine.NestedStateMachineBuilder<SimpleNestedAgainMachineContext,
                    SimpleNestedStateMachine.SimpleNestedMachineContext>()
                    .setStateMachineContextFactory(new SimpleNestedAgainMachineContext())
                    .initialState(States.STATE)
                    .states(EnumSet.allOf(States.class))
                    .build();

    //private static final ProgressingAction<ActorMessage, SimpleNestedAgainMachineContext> action = new
    // ProgressingActionBuilder.Builder<SimpleNestedAgainMachineContext>().build();
    private static final Action<SimpleNestedAgainMachineContext> action = (context) -> {
        log.trace(STR."parent context access from nested again: \{SimpleParentStateMachine.CONTEXT.get().string}");
        log.trace(STR."nested context access from nested again: \{SimpleNestedStateMachine.CONTEXT.get().string}");
        log.trace(STR."unbound: \{SimpleNestedStateMachine.CONTEXT.get().string}");
        log.trace(context.string);
        return context;
    };

    private SimpleNestedAgainStateMachine() {
        // no op, static state-machine config
    }

    private enum States implements StateMachineStateConfiguration<SimpleNestedAgainMachineContext> {
        STATE {
            public State<SimpleNestedAgainMachineContext> state() {
                return new StateMachineState.StateMachineStateBuilder<SimpleNestedAgainMachineContext>()
                        .setAction(action)
                        .build();
            }

            public StateMachineTransition<SimpleNestedAgainMachineContext> transition() {
                return StateMachineTransition.end();
            }
        }
    }

    public static class SimpleNestedAgainMachineContext implements StateMachineContext<SimpleNestedAgainMachineContext>,
            StateMachineContextFactory<SimpleNestedAgainMachineContext> {
        public String string = "SM contextual string [Nested Again]";
        public int num = 0;

        @Override
        public SimpleNestedAgainStateMachine.SimpleNestedAgainMachineContext newInstance() {
            return new SimpleNestedAgainStateMachine.SimpleNestedAgainMachineContext();
        }
    }
}
