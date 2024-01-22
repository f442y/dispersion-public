package net.fa.dispersion.stateactorsystem.statemachine.simple_test_statemachines;

import lombok.extern.slf4j.Slf4j;
import net.fa.dispersion.stateactorsystem.statemachine.NestedStateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.NestedStateMachineConfiguration;
import net.fa.dispersion.stateactorsystem.statemachine.action.NestedTaskScopeAction;
import net.fa.dispersion.stateactorsystem.statemachine.action.ProgressingAction;
import net.fa.dispersion.stateactorsystem.statemachine.action.WaitingAction;
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
public class SimpleNestedStateMachine extends NestedStateMachineConfiguration<SimpleParentStateMachine> {
    public static final NestedStateMachine<SimpleNestedMachineContext,
            SimpleParentStateMachine.SimpleParentMachineContext>
            stateMachine =
            new NestedStateMachine.NestedStateMachineBuilder<SimpleNestedMachineContext,
                    SimpleParentStateMachine.SimpleParentMachineContext>()
                    .setStateMachineContextFactory(new SimpleNestedMachineContext())
                    .completionPolicy("New policy")
                    .initialState(States.STATE_START)
                    .states(EnumSet.allOf(States.class))
                    .build();
    protected static final ScopedValue<SimpleNestedMachineContext> CONTEXT = ScopedValue.newInstance();
    //        private static final ProgressingAction<ActorMessage, SimpleNestedMachineContext> action = new
    //        ProgressingActionBuilder.Builder<SimpleNestedMachineContext>().build();
    private static final ProgressingAction<SimpleNestedMachineContext> action = (context) -> {
        log.trace(STR."parent context access from nested: \{SimpleParentStateMachine.CONTEXT.get().string}");
        log.trace(
                STR."nested again context access from nested: is bound? \{SimpleNestedAgainStateMachine.CONTEXT.isBound()}");
        log.trace(context.string);
        return context;
    };

    private static final NestedTaskScopeAction<SimpleNestedMachineContext,
            SimpleNestedAgainStateMachine.SimpleNestedAgainMachineContext>
            nestedSMAction =
            new NestedTaskScopeAction.Builder<SimpleNestedMachineContext,
                    SimpleNestedAgainStateMachine.SimpleNestedAgainMachineContext>()
                    .name("nested-again")
                    .addScopedParentContext(CONTEXT)
                    .addStateMachine(SimpleNestedAgainStateMachine.stateMachine)
                    .addStateMachine(SimpleNestedAgainStateMachine.stateMachine)
                    .addStateMachine(SimpleNestedAgainStateMachine.stateMachine)
                    .build();

    private static final WaitingAction<SimpleNestedMachineContext> waitingAction = (actorMessage, context) -> {
        return context;
    };

    private SimpleNestedStateMachine() {
        // no op, static state-machine config
    }

    private static Transition<SimpleNestedMachineContext> conditionalTransition(SimpleNestedMachineContext stateMachineContext) {
        log.trace("conditional transition running");
        return Become(States.STATE_END);
    }

    private enum States implements StateMachineStateConfiguration<SimpleNestedMachineContext> {

        STATE_START {
            public State<SimpleNestedMachineContext> state() {
                return new StateMachineState.StateMachineStateBuilder<SimpleNestedMachineContext>()
                        .setAction(nestedSMAction)
                        .build();
            }

            public StateMachineTransition<SimpleNestedMachineContext> transition() {
                return StateMachineTransition
                        .conditionalTransition()
                        .condition(SimpleNestedStateMachine::conditionalTransition);
            }
        },

        STATE_END {
            public State<SimpleNestedMachineContext> state() {
                return new StateMachineState.StateMachineStateBuilder<SimpleNestedMachineContext>()
                        .setAction(action)
                        .build();
            }

            public StateMachineTransition<SimpleNestedMachineContext> transition() {
                return StateMachineTransition.end();
            }
        }
    }

    public static class SimpleNestedMachineContext implements StateMachineContext<SimpleNestedMachineContext>,
            StateMachineContextFactory<SimpleNestedMachineContext> {
        public String string = "SM contextual string [Nested]";
        public int num = 0;

        @Override
        public SimpleNestedMachineContext newInstance() {
            return new SimpleNestedMachineContext();
        }
    }
}
