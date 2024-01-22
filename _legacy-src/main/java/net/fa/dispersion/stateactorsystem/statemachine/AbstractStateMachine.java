package net.fa.dispersion.stateactorsystem.statemachine;

import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContextFactory;
import net.fa.dispersion.stateactorsystem.statemachine.state.StateMachineStateConfiguration;

import java.util.EnumSet;
import java.util.function.Function;

/**
 * This abstract class is the basis of every statemachine.
 * Every statemachine will have its states defined as a set of Enums which are of type
 * {@link StateMachineStateConfiguration}.
 * The initial state should be defined.
 * <p>
 * Every statemachine will have its context defined, which is of type {@link StateMachineContext}.
 * The context will be passed throughout every {@link net.fa.dispersion.stateactorsystem.statemachine.state.State}
 * and {@link net.fa.dispersion.stateactorsystem.statemachine.transition.Transition},
 * therefore, the context's type must be fixed and provided as a generic 'CTX'.
 *
 * @param <CTX> The type of the context which will be passed through the statemachine
 * @author Faizaan Ahmed
 * @see StateMachine
 * @see NestedStateMachine
 * @see StateMachineStateConfiguration
 */
public sealed abstract class AbstractStateMachine<CTX extends StateMachineContext<CTX>, RTN> permits NestedStateMachine,
        StateMachine {
    public final EnumSet<? extends StateMachineStateConfiguration<CTX>> stateEnums;
    public final StateMachineStateConfiguration<CTX> initialStateMachineStateConfiguration;
    public final StateMachineContextFactory<CTX> stateMachineContextFactory;
    public Function<CTX, RTN> returnFunction;

    protected AbstractStateMachine(AbstractStateMachineBuilder<?, ?, CTX> stateMachineBuilder) {
        this.stateEnums = stateMachineBuilder.stateEnums;
        this.initialStateMachineStateConfiguration = stateMachineBuilder.initialStateMachineStateConfiguration;
        this.stateMachineContextFactory = stateMachineBuilder.stateMachineContextFactory;
    }

    public abstract sealed static class AbstractStateMachineBuilder<B extends AbstractStateMachineBuilder<B, T, CTX>,
            T, CTX extends StateMachineContext<CTX>> permits
            NestedStateMachine.NestedStateMachineBuilder,
            StateMachine.StateMachineBuilder {
        public CTX stateMachineContext;
        public StateMachineContextFactory<CTX> stateMachineContextFactory;
        private EnumSet<? extends StateMachineStateConfiguration<CTX>> stateEnums;
        private StateMachineStateConfiguration<CTX> initialStateMachineStateConfiguration;

        abstract public B builderType();

        public B states(EnumSet<? extends StateMachineStateConfiguration<CTX>> stateEnums) {
            this.stateEnums = stateEnums;
            return builderType();
        }

        public B initialState(StateMachineStateConfiguration<CTX> stateMachineStateConfiguration) {
            this.initialStateMachineStateConfiguration = stateMachineStateConfiguration;
            return builderType();
        }

        /**
         * As a new instance of context is created for each new statemachine callable, A factory class is used to
         * create a context object.
         *
         * @param stateMachineContextFactory The factory class used to create the {@link StateMachineContext<CTX>}
         */
        public B setStateMachineContextFactory(StateMachineContextFactory<CTX> stateMachineContextFactory) {
            this.stateMachineContextFactory = stateMachineContextFactory;
            return builderType();
        }

        public abstract T build();
    }
}
