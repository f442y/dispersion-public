//package net.fa.dispersion.stateactorsystem;
//
//import net.fa.dispersion.stateactorsystem.ActorRunnables.StateMachineRunnableActor;
//import net.fa.dispersion.stateactorsystem.ActorRunnables.StateRunnableActorAddress;
//import net.fa.dispersion.stateactorsystem.message.ActorMessage;
//import net.fa.dispersion.stateactorsystem.actors.Actor;
//import net.fa.dispersion.stateactorsystem.statemachine.state.State;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//import java.util.function.Function;
//
//public class ActorFactory {
//    private static final Logger logger = LoggerFactory.getLogger(ActorFactory.class);
//    private static final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
//
//    public static StateMachineActor.ActorAddress<ActorMessage> newStateActor(Function<StateMachineActor
//    .ActorAddress<ActorMessage>, StateMachineActor.State<ActorMessage>> initialStateFunction) {
//        var runnableAddress = new StateRunnableActorAddress(initialStateFunction);
//        executorService.execute(runnableAddress);
//        return runnableAddress;
//    }
//
//    public static StateActorAddressWithFuture newStateActorWithFuture(Function<StateMachineActor
//    .ActorAddress<ActorMessage>, StateMachineActor.State<ActorMessage>> initialStateFunction) {
//        var runnableAddress = new StateRunnableActorAddress(initialStateFunction);
//        Future<?> future = executorService.submit(runnableAddress);
//        return new StateActorAddressWithFuture(runnableAddress, future);
//    }
//
////    public static StateMachineActorAddressWithFuture newStateMachineActorWithFuture(Function<Actor<ActorMessage>,
// State<ActorMessage>> initialState) {
////        var runnableAddress = new StateMachineRunnableActor(initialState);
////        Future<?> future = executorService.submit(runnableAddress);
////        return new StateMachineActorAddressWithFuture(runnableAddress, future);
////    }
//
//    public static StateMachineActorWithFuture newStateMachineActorWithFuture(Function<Actor<ActorMessage>,
//    State<ActorMessage>> initialState) {
//        var runnableActor = new StateMachineRunnableActor(initialState);
//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(runnableActor, executorService);
//        return new StateMachineActorWithFuture(runnableActor, completableFuture);
//    }
//}
