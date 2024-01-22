package net.fa.dispersion.stateactorsystem.statemachine.simple_test_statemachines;

import lombok.extern.slf4j.Slf4j;
import net.fa.dispersion.stateactorsystem.statemachine.action.Action;

@Slf4j
public class Actions {
    public static final Action<SimpleParentStateMachine.SimpleParentMachineContext> action =
            (context) -> {
                log.trace(context.string);
                return context;
            };
}
