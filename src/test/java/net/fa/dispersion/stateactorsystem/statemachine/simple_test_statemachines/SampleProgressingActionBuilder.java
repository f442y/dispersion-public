package net.fa.dispersion.stateactorsystem.statemachine.simple_test_statemachines;

import lombok.extern.slf4j.Slf4j;
import net.fa.dispersion.stateactorsystem.statemachine.action.ProgressingAction;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

@Slf4j
public class SampleProgressingActionBuilder {

    public static class Builder<CTX extends StateMachineContext<CTX>> {
        public ProgressingAction<CTX> build() {
            return (stateMachineContext) -> {
                log.info("Running Simple Action");
                int num = 1 + 1000;
                int numm = 1 + 10000;
                int nummm = 1 - 10000;
                int total = nummm + numm + num;
                return stateMachineContext;
            };
        }
    }
}
