package net.fa.dispersion.stateactorsystem.statemachine.transition;

import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;
import net.fa.dispersion.stateactorsystem.statemachine.state.StateMachineStateConfiguration;

import java.util.function.Function;

/**
 * Class used to hold and build a {@link StateMachineTransition} which is used by the
 * {@link net.fa.dispersion.stateactorsystem.actor.StateMachineActor} event loop to get the
 * {@link Transition} that should be applied to the statemachine.
 * <p>
 * The {@link #getNextStateConfiguration(StateMachineContext)} function is used by
 * {@link net.fa.dispersion.stateactorsystem.actor.StateMachineActor}
 * to extract the {@link Transition}.
 * <p>
 * This class allows the transitioning of the statemachine to be (optionally) 'context aware'.
 * A {@link StateMachineTransition} can have a function which transitions a specific
 * {@link StateMachineStateConfiguration}
 * based on the context of the statemachine.
 *
 * @param <CTX> The context type of the statemachine
 * @author Faizaan Ahmed
 */
public class StateMachineTransition<CTX extends StateMachineContext<CTX>> {

    private final Function<CTX, Transition<CTX>> transitionFunction;

    private StateMachineTransition(StateMachineStateConfiguration<CTX> stateMachineStateConfiguration) {
        this.transitionFunction = _ -> Transition.Become(stateMachineStateConfiguration);
    }

    private StateMachineTransition(Function<CTX, Transition<CTX>> conditionalTransitionFunction) {
        this.transitionFunction = conditionalTransitionFunction;
    }

    private StateMachineTransition(Transition<CTX> transition) {
        this.transitionFunction = _ -> transition;
    }

    /**
     * Static utility function to return an End/Kill {@link Transition}
     *
     * @return {@link Transition#End()}
     */
    public static <CTX extends StateMachineContext<CTX>> StateMachineTransition<CTX> end() {
        return new StateMachineTransition<CTX>(Transition.End());
    }

    /**
     * Static utility function to return a Stay in same state {@link Transition}
     *
     * @return {@link Transition#Stay()}
     */
    public static <CTX extends StateMachineContext<CTX>> StateMachineTransition<CTX> stay() {
        return new StateMachineTransition<CTX>(Transition.Stay());
    }

    /**
     * Begin building a simple transition.
     *
     * @return {@link SimpleTransitionBuilder}
     */
    public static SimpleTransitionBuilder transition() {
        return new SimpleTransitionBuilder();
    }

    /**
     * Begin building a complex/conditional transition.
     *
     * @return {@link ConditionalTransitionBuilder}
     */
    public static ConditionalTransitionBuilder conditionalTransition() {
        return new ConditionalTransitionBuilder();
    }

    /**
     * Function used by {@link net.fa.dispersion.stateactorsystem.actor.StateMachineActor} to get {@link Transition}
     * which is to be applied to the statemachine.
     * <p>
     * This function applies the {@link #transitionFunction} to the statemachines context which should give back a
     * {@link Transition}.
     *
     * @param stateMachineContext The statemachines context
     * @return A {@link Transition} resolved by applying the {@link #transitionFunction} built by the
     * {@link StateMachineStateConfiguration}
     */
    public Transition<CTX> getNextStateConfiguration(CTX stateMachineContext) {
        return transitionFunction.apply(stateMachineContext);
    }

    /**
     * Builder class used to build a simple unconditional transition not involving context.
     */
    public static class SimpleTransitionBuilder {
        /**
         * To build an End/Kill state transition use the predefined {@link StateMachineTransition} method
         * `{@link #end()}`.
         * <p>
         * To build a stay in same state transition use the predefined {@link StateMachineTransition} method
         * `{@link #stay()}`.
         * <p>
         * Use this to build a transition to a different (non end/kill) state.
         *
         * @param nextState The {@link StateMachineStateConfiguration} to transition to.
         * @return A {@link StateMachineTransition} which resolves to a {@link Transition} to the defined state.
         */
        public <CTX extends StateMachineContext<CTX>> StateMachineTransition<CTX> nextState(StateMachineStateConfiguration<CTX> nextState) {
            return new StateMachineTransition<>(nextState);
        }
    }

    /**
     * Builder class used to build a conditional transition which can optionally involve statemachine context.
     */
    public static class ConditionalTransitionBuilder {
        // todo: define all possible states
        // todo: add default transition for skips(???)

        /**
         * @param conditionalTransitionFunction A function which takes in the statemachines context and returns a
         *                                      {@link Transition}.
         * @return A {@link StateMachineTransition} which resolves to a {@link Transition} after running the function
         * passed in.
         */
        public <CTX extends StateMachineContext<CTX>> StateMachineTransition<CTX> condition(Function<CTX,
                Transition<CTX>> conditionalTransitionFunction) {
            return new StateMachineTransition<>(conditionalTransitionFunction);

        }
    }
}
