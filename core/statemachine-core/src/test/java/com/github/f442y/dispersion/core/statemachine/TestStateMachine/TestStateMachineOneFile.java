//package com.github.f442y.dispersion.core.statemachine.TestStateMachine;
//
//
//import com.github.f442y.dispersion.core.statemachine.context.StateMachineContext;
//import com.github.f442y.dispersion.core.statemachine.context.StateMachineContextFactory;
//import com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.multi_context_action
// .ActionForContextWithSampleContextInterface;
//import com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.multi_context_action
// .SampleContextInterface;
//import com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.simpleNumAddition.dependencies
// .ActionSimpleNumAdditionDependencies;
//import com.github.f442y.dispersion.core.statemachine.exception.ActionException;
//import com.github.f442y.dispersion.core.statemachine.exception.StateMachineException;
//import com.github.f442y.dispersion.core.statemachine.exception.TransitionException;
//import com.github.f442y.dispersion.core.statemachine.state.StateConfiguration;
//import com.github.f442y.dispersion.core.statemachine.state.StateKey;
//import com.github.f442y.dispersion.core.statemachine.state.action.Action;
//import com.github.f442y.dispersion.core.statemachine.state.transition.Transition;
//import com.github.f442y.dispersion.core.statemachine.state.transition.TransitionStateKey;
//import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.InputFunction;
//import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.OutputFunction;
//import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMachineConfiguration;
//import com.github.f442y.dispersion.core.statemachine.statemachineconfiguration.StateMapBuilder;
//import lombok.NonNull;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class TestStateMachineOneFile
//        extends StateMachineConfiguration<TestStateMachineOneFile.OneFileTestStateMachineContext,
//        TestStateMachineOneFile.StateKeys,
//        Integer, Integer> {
//    private static final OneFileTestStateMachineContext CONTEXT_FACTORY = new OneFileTestStateMachineContext();
//    private static final ActionOne ACTION_ONE = new ActionOne(new ActionSimpleNumAdditionDependencies());
//    private static final ActionTwo ACTION_TWO = new ActionTwo(new ActionSimpleNumAdditionDependencies());
//
//    public enum StateKeys implements StateKey {
//        A, B, C, END
//    }
//
//    public TestStateMachineOneFile() {
//        super(StateMapBuilder
//                .<OneFileTestStateMachineContext, StateKeys>buildStateMap(false)
//                .states(StateKeys.class)
//                .addStateMapping(StateKeys.A, new StateA())
//                .addStateMapping(StateKeys.B, new StateB())
//                .addStateMapping(StateKeys.C, new StateC())
////                .addStateMapping(States.C, new StateC())
//                .done()
//                .initialState(StateKeys.A)
//                .endState(StateKeys.END)
//                .build());
//    }
//
//    @Override
//    public StateMachineContextFactory<OneFileTestStateMachineContext> stateMachineContextFactory() {
//        return CONTEXT_FACTORY;
//    }
//
//    @Override
//    public InputFunction<OneFileTestStateMachineContext, Integer> inputFunction() throws StateMachineException {
//        return (oneFileTestStateMachineContext, inputInteger) -> {
//            oneFileTestStateMachineContext.num = inputInteger;
//            return oneFileTestStateMachineContext;
//        };
//    }
//
//    @Override
//    public OutputFunction<OneFileTestStateMachineContext, Integer> outputFunction() throws StateMachineException {
//        return oneFileTestStateMachineContext -> oneFileTestStateMachineContext.num;
//    }
//
//    private static class ActionOne extends Action<OneFileTestStateMachineContext,
//    ActionSimpleNumAdditionDependencies> {
//
//        public ActionOne(@NonNull ActionSimpleNumAdditionDependencies actionSimpleNumAdditionDependencies) {
//            super(actionSimpleNumAdditionDependencies);
//        }
//
//        @Override
//        public @NonNull TestStateMachineOneFile.OneFileTestStateMachineContext action(
//                OneFileTestStateMachineContext stateMachineContext,
//                ActionSimpleNumAdditionDependencies actionDependencies
//        ) throws ActionException {
//            for (int i = 0; i < 10; i++) {
//                stateMachineContext.num++;
//                stateMachineContext.string = String.valueOf(stateMachineContext.num);
//            }
//            return stateMachineContext;
//        }
//    }
//
//    private static class ActionTwo extends
//    ActionForContextWithSampleContextInterface<OneFileTestStateMachineContext> {
//
//        public ActionTwo(@NonNull ActionSimpleNumAdditionDependencies actionSimpleNumAdditionDependencies) {
//            super(actionSimpleNumAdditionDependencies);
//        }
//
//        @Override
//        public @NonNull TestStateMachineOneFile.OneFileTestStateMachineContext action(
//                OneFileTestStateMachineContext stateMachineContext,
//                ActionSimpleNumAdditionDependencies actionDependencies
//        ) throws ActionException {
//            for (int i = 0; i < 10; i++) {
//                stateMachineContext.num++;
//                stateMachineContext.string = String.valueOf(stateMachineContext.num);
//                stateMachineContext.sampleContextSpecificMethod();
//            }
//            return stateMachineContext;
//        }
//    }
//
//    private static class StateA extends StateConfiguration<OneFileTestStateMachineContext, StateKeys> {
//
//        private static final Transition<OneFileTestStateMachineContext, StateKeys, TSAKey> TRANSITION_STATE_A =
//                new Transition<>(TSAKey.class) {
//                    @Override
//                    public TSAKey resolvePermittedTransition(OneFileTestStateMachineContext stateMachineContext
//                    ) throws TransitionException {
//                        return TSAKey.CtoEND;
//                    }
//                };
//
//        @Override
//        public Action<OneFileTestStateMachineContext, ActionSimpleNumAdditionDependencies> action() {
//            return ACTION_ONE;
//        }
//
//        @Override
//        public Transition<OneFileTestStateMachineContext, StateKeys, TSAKey> transition() {
//            return TRANSITION_STATE_A;
//        }
//
//        public enum TSAKey implements TransitionStateKey<StateKeys> {
//            CtoEND {
//                @Override
//                public StateKeys stateKey() {
//                    return StateKeys.B;
//                }
//            }
//        }
//    }
//
//    private static class StateB extends StateConfiguration<OneFileTestStateMachineContext, StateKeys> {
//
//        private static final Transition<OneFileTestStateMachineContext, StateKeys, TSBKey> TRANSITION_STATE_B =
//                new Transition<>(TSBKey.class) {
//                    @Override
//                    public TSBKey resolvePermittedTransition(OneFileTestStateMachineContext stateMachineContext
//                    ) throws TransitionException {
//                        return TSBKey.BtoC;
//                    }
//                };
//
//        @Override
//        public Action<OneFileTestStateMachineContext, ActionSimpleNumAdditionDependencies> action() {
//            return ACTION_TWO;
//        }
//
//        @Override
//        public Transition<OneFileTestStateMachineContext, StateKeys, TSBKey> transition() {
//            return TRANSITION_STATE_B;
//        }
//
//        public enum TSBKey implements TransitionStateKey<StateKeys> {
//            BtoC(StateKeys.C), BtoEnd(StateKeys.END);
//
//            private final StateKeys nextState;
//
//            TSBKey(StateKeys nextState) {
//                this.nextState = nextState;
//            }
//
//            @Override
//            public StateKeys stateKey() {
//                return nextState;
//            }
//        }
//    }
//
//    private static class StateC extends StateConfiguration<OneFileTestStateMachineContext, StateKeys> {
//
//        private static final Transition<OneFileTestStateMachineContext, StateKeys, TSCKey> TRANSITION_STATE_C =
//                new Transition<>(TSCKey.class) {
//                    @Override
//                    public TSCKey resolvePermittedTransition(OneFileTestStateMachineContext stateMachineContext
//                    ) throws TransitionException {
//                        return TSCKey.CtoEND;
//                    }
//                };
//
//        @Override
//        public Action<OneFileTestStateMachineContext, ActionSimpleNumAdditionDependencies> action() {
//            return ACTION_ONE;
//        }
//
//        @Override
//        public Transition<OneFileTestStateMachineContext, StateKeys, TSCKey> transition() {
//            return TRANSITION_STATE_C;
//        }
//
//        public enum TSCKey implements TransitionStateKey<StateKeys> {
//            CtoEND {
//                @Override
//                public StateKeys stateKey() {
//                    return StateKeys.END;
//                }
//            }
//        }
//    }
//
//    public static class OneFileTestStateMachineContext implements StateMachineContext,
//            StateMachineContextFactory<OneFileTestStateMachineContext>,
//            SampleContextInterface {
//        public String string = "Simple Contextual String (Test)";
//        public int num = 0;
//
//        @Override
//        public OneFileTestStateMachineContext newInstance() {
//            return new OneFileTestStateMachineContext();
//        }
//
//        @Override
//        public void sampleContextSpecificMethod() {
////            log.info("SampleContextInterface accessed");
//        }
//    }
//}
