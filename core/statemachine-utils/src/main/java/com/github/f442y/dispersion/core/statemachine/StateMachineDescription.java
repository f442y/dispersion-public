package com.github.f442y.dispersion.core.statemachine;

import com.github.f442y.dispersion.core.service.Service;
import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
import com.github.f442y.dispersion.core.statemachine.dependency.ActionDependencies;
import com.github.f442y.dispersion.core.statemachine.state.State;
import com.github.f442y.dispersion.core.statemachine.state.StateKey;
import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class StateMachineDescription {
    private StateMachineDescription() {/* no-op */}

    public static <CONTEXT extends StateMachineContext, STATE_KEY extends Enum<STATE_KEY> & StateKey> void buildDescriptionFromStateMap(
            StateMap<CONTEXT, STATE_KEY> stateMap
    ) {
        Set<Service> serviceDependencyRegistries = new HashSet<>();

        // for each state
        stateMap.stateEnumMap().values().forEach(state -> {
            if (Objects.requireNonNull(state) instanceof State<CONTEXT, STATE_KEY> stateConfiguration) {
                if (!(stateConfiguration instanceof State.EndState<CONTEXT, STATE_KEY>)) {
                    // view dependencies for the state
                    ActionDependencies<?, ?, ?> actionDependencies = stateConfiguration.action.viewDependencies();
                    actionDependencies.dataRegistry().ifPresent(System.out::println);
                    actionDependencies
                            .serviceRegistry()
                            .ifPresent(serviceDependencyRegistry -> serviceDependencyRegistries.addAll(
                                    serviceDependencyRegistry.allServiceDependencies()));
                    actionDependencies.systemStateRegistry().ifPresent(System.out::println);
                }
            }
        });
        System.out.println(serviceDependencyRegistries);

        List<StateMachineDescriptionAdjacencyListJson.State> states = new ArrayList<>();

        stateMap.stateEnumMap().values().forEach(stateConfigBase -> {
            if (Objects.requireNonNull(stateConfigBase) instanceof State<CONTEXT, STATE_KEY> stateConfiguration) {
                StateMachineDescriptionAdjacencyListJson.State state =
                        new StateMachineDescriptionAdjacencyListJson.State(stateConfiguration.toString(),
                                stateConfiguration.action == null ? null : stateConfiguration.action.toString(),
                                stateConfiguration.transition == null ? null : stateConfiguration.transition
                                        .getPossibleFutureStateKeys()
                                        .stream()
                                        .map(stateMap::getState)
                                        .map(sc -> new StateMachineDescriptionAdjacencyListJson.Transition(sc
                                                .getClass()
                                                .toString(),
                                                stateConfiguration.transition.getClass().getName()
                                        ))
                                        .toList()
                        );
                states.add(state);
            }
        });
        StateMachineDescriptionAdjacencyListJson sss = new StateMachineDescriptionAdjacencyListJson("statemachine",
                stateMap.getInitialState().getClass().toString(),
                states
        );

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();

        System.out.println(gson.toJson(sss));
    }

    private static class StateMachineDescriptionAdjacencyListJson {
        private String className;
        private String initialState;
        private List<State> allStates;

        public StateMachineDescriptionAdjacencyListJson(String className, String initialState, List<State> allStates) {
            this.className = className;
            this.initialState = initialState;
            this.allStates = allStates;
        }

        private static class State {
            private String className;
            private String actionName;
            private List<Transition> transitionList;

            public State(String className, String actionName, List<Transition> transitionList) {
                this.className = className;
                this.actionName = actionName;
                this.transitionList = transitionList;
            }
        }

        private static class Transition {
            private String transitionName;
            private String stateTo;

            public Transition(String stateTo, String transitionName) {
                this.stateTo = stateTo;
                this.transitionName = transitionName;
            }
        }
    }

}
