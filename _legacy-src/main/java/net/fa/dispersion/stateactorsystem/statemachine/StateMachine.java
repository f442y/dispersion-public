package net.fa.dispersion.stateactorsystem.statemachine;

import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

import java.util.function.Function;

public final class StateMachine<CTX extends StateMachineContext<CTX>, RTN> extends AbstractStateMachine<CTX, RTN> {

    private StateMachine(StateMachineBuilder<CTX, RTN> stateMachineBuilder) {
        super(stateMachineBuilder);
        super.returnFunction = stateMachineBuilder.returnFunction;
    }

    public static final class StateMachineBuilder<CTX extends StateMachineContext<CTX>, RTN> extends
            AbstractStateMachine.AbstractStateMachineBuilder<StateMachineBuilder<CTX, RTN>, StateMachine<CTX, RTN>,
                    CTX> {
        private Function<CTX, RTN> returnFunction;

        /**
         * (optional)
         *
         * @param returnFunction
         * @return
         */
        public StateMachineBuilder<CTX, RTN> returnFunction(Function<CTX, RTN> returnFunction) {
            this.returnFunction = returnFunction;
            return this;
        }

        @Override
        public StateMachineBuilder<CTX, RTN> builderType() {
            return this;
        }

        public StateMachine<CTX, RTN> build() {
            return new StateMachine<>(this);
        }
    }
}
