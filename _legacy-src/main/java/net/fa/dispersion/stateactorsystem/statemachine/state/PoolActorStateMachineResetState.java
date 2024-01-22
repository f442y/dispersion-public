//package net.fa.dispersion.stateactorsystem.statemachine.state;
//
//import lombok.Getter;
//import net.fa.dispersion.stateactorsystem.message.ActorMessage;
//import net.fa.dispersion.stateactorsystem.statemachine.action.Action;
//import net.fa.dispersion.stateactorsystem.statemachine.action.WaitingAction;
//import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;
//
//@Getter
//public class PoolActorStateMachineResetState<CTX extends StateMachineContext<CTX>> extends
// AbstractStateMachineState<CTX> {
//    public PoolActorStateMachineResetState(PoolActorStateMachineResetStateBuilder<CTX>
//    poolActorStateMachineResetStateBuilder) {
//        super(poolActorStateMachineResetStateBuilder);
//    }
//
//    public static class PoolActorStateMachineResetStateBuilder<CTX extends StateMachineContext<CTX>> extends
//            AbstractStateMachineStateBuilder<PoolActorStateMachineResetStateBuilder<CTX>,
//                    PoolActorStateMachineResetState<CTX>, CTX> {
//
//        @Override
//        public PoolActorStateMachineResetStateBuilder<CTX> builderType() {
//            return this;
//        }
//
//        @Override
//        public PoolActorStateMachineResetStateBuilder<CTX> setAction(Action<ActorMessage, CTX> action) {
//            if (!(action instanceof WaitingAction<ActorMessage, ? extends StateMachineContext>)) {
//                throw new ClassCastException("PoolActorStateMachineResetState should have a WaitingAction");
//            }
//            super.setAction(action);
//            return this;
//        }
//
//        public PoolActorStateMachineResetState<CTX> build() {
//            return new PoolActorStateMachineResetState<>(this);
//        }
//    }
//}
