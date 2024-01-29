package com.github.f442y.dispersion.core.statemachine.statemachineconfiguration;

import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.state.State;
import com.github.f442y.dispersion.core.statemachine.state.StateKey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

public class StateMapBuilder {

    public static <CONTEXT extends StateMachineContext, STATE_KEY extends Enum<STATE_KEY> & StateKey> StateMapBuilderSetStatesEnumStage<CONTEXT, STATE_KEY> buildStateMap(
            boolean skipStateMapValidation
    ) {
        return new StateMapBuilderSetStatesEnumStage<CONTEXT, STATE_KEY>(new StateMapBuilderObject<>(
                skipStateMapValidation));
    }

    static final class StateMapBuilderObject<CONTEXT extends StateMachineContext,
            STATE_KEY extends Enum<STATE_KEY> & StateKey> {
        public final Collection<StateMapBuilderException> exceptions = new ArrayList<>();
        private final boolean skipStateMapValidation;
        public EnumMap<STATE_KEY, State<CONTEXT, STATE_KEY>> builderValidationEnumStateMap;
        public STATE_KEY initialState;
        private STATE_KEY endState;
        private EnumSet<STATE_KEY> builderValidationEnumSet;

        private StateMapBuilderObject(boolean skipStateMapValidation) {
            this.skipStateMapValidation = skipStateMapValidation;
        }
    }

    private static abstract class StateMapBuilderStage<CONTEXT extends StateMachineContext,
            STATE_KEY extends Enum<STATE_KEY> & StateKey> {
        protected final StateMapBuilderObject<CONTEXT, STATE_KEY> stateMapBuilderObject;

        protected StateMapBuilderStage(StateMapBuilderObject<CONTEXT, STATE_KEY> stateMapBuilderObject) {
            this.stateMapBuilderObject = stateMapBuilderObject;
        }

        protected final void addException(StateMapBuilderException stateMapBuilderException) {
            this.stateMapBuilderObject.exceptions.add(stateMapBuilderException);
        }
    }

    public static final class StateMapBuilderSetStatesEnumStage<CONTEXT extends StateMachineContext,
            STATE_KEY extends Enum<STATE_KEY> & StateKey>
            extends StateMapBuilderStage<CONTEXT, STATE_KEY> {

        private StateMapBuilderSetStatesEnumStage(StateMapBuilderObject<CONTEXT, STATE_KEY> stateMapBuilderObject) {
            super(stateMapBuilderObject);
        }

        public StateMapBuilderAddStatesStage<CONTEXT, STATE_KEY> states(Class<STATE_KEY> statesEnumClass
        ) {
            super.stateMapBuilderObject.builderValidationEnumStateMap = new EnumMap<>(statesEnumClass);
            super.stateMapBuilderObject.builderValidationEnumSet = EnumSet.allOf(statesEnumClass);
            return new StateMapBuilderAddStatesStage<>(super.stateMapBuilderObject);
        }
    }

    public static final class StateMapBuilderAddStatesStage<CONTEXT extends StateMachineContext,
            STATE_KEY extends Enum<STATE_KEY> & StateKey>
            extends StateMapBuilderStage<CONTEXT, STATE_KEY> {

        private StateMapBuilderAddStatesStage(StateMapBuilderObject<CONTEXT, STATE_KEY> stateMapBuilderObject) {
            super(stateMapBuilderObject);
        }

        public StateMapBuilderAddStatesStage<CONTEXT, STATE_KEY> addStateMapping(STATE_KEY stateKey,
                                                                                 State<CONTEXT,
                                                                                                                                                                          STATE_KEY> state
        ) {
            if (!stateMapBuilderObject.skipStateMapValidation) {
                // validate all state configs are unique
                // validate all states are only in map once
                if (super.stateMapBuilderObject.builderValidationEnumStateMap.containsValue(state)) {
                    super.addException(new StateMapBuilderException("state configuration " + (state
                            .getClass()
                            .getName()) + " is already in the statemap"));
                }
                if (super.stateMapBuilderObject.builderValidationEnumStateMap.containsKey(stateKey)) {
                    super.addException(new StateMapBuilderException("state key (" + stateKey + ") " + "is already in "
                            + "the statemap"));
                }
            }
            super.stateMapBuilderObject.builderValidationEnumStateMap.put(stateKey, state);
            return this;
        }

        public StateMapBuilderSetInitialStateStage<CONTEXT, STATE_KEY> done() {
            return new StateMapBuilderSetInitialStateStage<>(super.stateMapBuilderObject);
        }
    }

    public static final class StateMapBuilderSetInitialStateStage<CONTEXT extends StateMachineContext,
            STATE_KEY extends Enum<STATE_KEY> & StateKey>
            extends StateMapBuilderStage<CONTEXT, STATE_KEY> {

        private StateMapBuilderSetInitialStateStage(StateMapBuilderObject<CONTEXT, STATE_KEY> stateMapBuilderObject) {
            super(stateMapBuilderObject);
        }

        public StateMapBuilderSetEndStateStage<CONTEXT, STATE_KEY> initialState(STATE_KEY initialStateKey) {
            if (!stateMapBuilderObject.skipStateMapValidation) {
                if (super.stateMapBuilderObject.builderValidationEnumStateMap.get(initialStateKey) == null) {
                    super.addException(new StateMapBuilderException("cannot make " + initialStateKey.toString() + " " + "the initial " + "state, as it has not yet been added to the stateMap via addStateMapping" + "()"));
                }
            }

            super.stateMapBuilderObject.initialState = initialStateKey;
            return new StateMapBuilderSetEndStateStage<>(super.stateMapBuilderObject);
        }
    }

    public static final class StateMapBuilderSetEndStateStage<CONTEXT extends StateMachineContext,
            STATE_KEY extends Enum<STATE_KEY> & StateKey>
            extends StateMapBuilderStage<CONTEXT, STATE_KEY> {

        private StateMapBuilderSetEndStateStage(StateMapBuilderObject<CONTEXT, STATE_KEY> stateMapBuilderObject) {
            super(stateMapBuilderObject);
        }

        public StateMapBuilderFinalStage<CONTEXT, STATE_KEY> endState(STATE_KEY endStateKey) {
            if (!stateMapBuilderObject.skipStateMapValidation) {
                if (super.stateMapBuilderObject.builderValidationEnumStateMap.get(endStateKey) != null) {
                    super.addException(new StateMapBuilderException(endStateKey.toString() + " is " + "already in " +
                            "the" + " statemap, cannot make it an end state"));
                }
            }

            super.stateMapBuilderObject.endState = endStateKey;
            super.stateMapBuilderObject.builderValidationEnumStateMap.put(
                    endStateKey,
                    new State.EndState<>()
            );
            return new StateMapBuilderFinalStage<>(super.stateMapBuilderObject);
        }
    }

    public static final class StateMapBuilderFinalStage<CONTEXT extends StateMachineContext,
            STATE_KEY extends Enum<STATE_KEY> & StateKey>
            extends StateMapBuilderStage<CONTEXT, STATE_KEY> {

        private StateMapBuilderFinalStage(StateMapBuilderObject<CONTEXT, STATE_KEY> stateMapBuilderObject) {
            super(stateMapBuilderObject);
        }

        private void validateStateMap() {
            // validate all states defined
            super.stateMapBuilderObject.builderValidationEnumSet.forEach(stateKey -> {
                if (null == super.stateMapBuilderObject.builderValidationEnumStateMap.get(stateKey)) {
                    super.addException(new StateMapBuilderException("state mapping not added for state key: (" + stateKey.toString() + ")"));
                }
            });

            // validate state paths
            // create graph
            HashMap<STATE_KEY, Set<STATE_KEY>> adjacencyListGraphMap =
                    buildAdjacencyListGraphMap(super.stateMapBuilderObject);
            // traverse state paths through graph
            recursiveValidateNoCyclicStateTransitions(
                    new ArrayList<>(Collections.singletonList(super.stateMapBuilderObject.initialState)),
                    adjacencyListGraphMap
            );
        }

        private HashMap<STATE_KEY, Set<STATE_KEY>> buildAdjacencyListGraphMap(
                StateMapBuilderObject<CONTEXT, STATE_KEY> stateMapBuilderObject
        ) {
            HashMap<STATE_KEY, Set<STATE_KEY>> adjacencyListGraphMap = new HashMap<>();
            stateMapBuilderObject.builderValidationEnumStateMap.forEach((stateKey, stateConfiguration) -> {
                if (!stateKey.equals(stateMapBuilderObject.endState)) {
                    adjacencyListGraphMap.put(stateKey, stateConfiguration.transition.getPossibleFutureStateKeys());
                }
            });
            return adjacencyListGraphMap;
        }

        private void recursiveValidateNoCyclicStateTransitions(ArrayList<STATE_KEY> stateChain,
                                                               HashMap<STATE_KEY, Set<STATE_KEY>> adjacencyListGraphMap
        ) {
            Set<STATE_KEY> nextStates = adjacencyListGraphMap.get(stateChain.getLast());
            nextStates.forEach(nextState -> {
                ArrayList<STATE_KEY> appendedStateChain = new ArrayList<>(stateChain);
                appendedStateChain.add(nextState);
//                log.info(appendedStateChain.toString());
                // check if state is already in state chain
                if (stateChain.contains(nextState)) {
//                    log.error("error, state ({}) already exists in chain: {}", nextState, appendedStateChain);
                    return;
                }
                // if end state, then return to previous state in chain
                if (nextState.equals(stateMapBuilderObject.endState)) {
//                    log.info("end of chain reached");
                    return;
                }

                // unique state, continue chain until end state or repeat state
                recursiveValidateNoCyclicStateTransitions(appendedStateChain, adjacencyListGraphMap);
            });
        }

        private void checkExceptions() {
            if (super.stateMapBuilderObject.exceptions.isEmpty()) {
                return;
            }
            super.stateMapBuilderObject.exceptions.forEach(System.out::println);
//            this.stateMapBuilderObject.exceptions.forEach(DispersionApplication
//            .RUNTIME_BOOT_VALIDATION::addStateMapException);
//            throw new StateMapBuilderException("check logs");
        }

        public StateMap<CONTEXT, STATE_KEY> build() {
            if (!stateMapBuilderObject.skipStateMapValidation) {
                this.validateStateMap();
                this.checkExceptions();
            }
            return new StateMap<>(super.stateMapBuilderObject);
        }
    }
}
