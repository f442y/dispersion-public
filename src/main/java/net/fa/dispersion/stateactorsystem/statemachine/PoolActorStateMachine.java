//package net.fa.dispersion.stateactorsystem.statemachine;
//
//import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;
//
//public class PoolActorStateMachine<CTX extends StateMachineContext<CTX>> extends AbstractStateMachine<CTX> {
//
//    private PoolActorStateMachine(PoolActorStateMachineBuilder<CTX> poolActorStateMachineBuilder) {
//        super(poolActorStateMachineBuilder);
//    }
//
//
//    public static final class PoolActorStateMachineBuilder<CTX extends StateMachineContext<CTX>> extends
//            AbstractStateMachine.AbstractStateMachineBuilder<PoolActorStateMachineBuilder<CTX>,
//                    PoolActorStateMachine<CTX>, CTX> {
//
//        @Override
//        public PoolActorStateMachineBuilder<CTX> builderType() {
//            return this;
//        }
//
//        public PoolActorStateMachine<CTX> build() {
//            return new PoolActorStateMachine<>(this);
//        }
//    }
//}
