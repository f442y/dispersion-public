//package net.fa.dispersion.stateactorsystem;
//
//import net.fa.dispersion.stateactorsystem.message.ActorMessage;
//import org.springframework.lang.NonNull;
//
//public interface StateMachineActor {
//    static <M extends ActorMessage> Transition<M> Kill() {
//        return originalState -> new KillState<>();
//    }
//
//    static <M extends ActorMessage> Transition<M> Become(State<M> nextState) {
//        return originalState -> nextState;
//    }
//
//    static <M extends ActorMessage> Transition<M> Stay() {
//        return originalState -> originalState;
//    }
//
//    @FunctionalInterface
//    interface Transition<M extends ActorMessage> {
//        State<M> applyToState(State<M> originalState);
//    }
//
//    sealed interface State<M extends ActorMessage> permits WaitingState, ProgressingState, KillState {
//    }
//
//    @FunctionalInterface
//    non-sealed interface WaitingState<M extends ActorMessage> extends State<M> {
//        Transition<M> receiveMessage(M o);
//    }
//
//    @FunctionalInterface
//    non-sealed interface ProgressingState<M extends ActorMessage> extends State<M> {
//        Transition<M> progress();
//    }
//
//    interface ActorAddress<M extends ActorMessage> {
//        ActorAddress<M> tell(@NonNull M msg);
//    }
//
//    non-sealed class KillState<M extends ActorMessage> implements State<M> {
//    }
//}
