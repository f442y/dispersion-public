package net.fa.dispersion.stateactorsystem.statemachine;

import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;

public final class NestedStateMachine<CTX extends StateMachineContext<CTX>,
        PARENT_CTX extends StateMachineContext<PARENT_CTX>> extends
        AbstractStateMachine<CTX, Void> {
    public final String completionPolicy;

    private NestedStateMachine(NestedStateMachineBuilder<CTX, PARENT_CTX> stateMachineBuilder) {
        super(stateMachineBuilder);
        this.completionPolicy = stateMachineBuilder.completionPolicy;
    }

    public static final class NestedStateMachineBuilder<CTX extends StateMachineContext<CTX>,
            PARENT_CTX extends StateMachineContext<PARENT_CTX>> extends
            AbstractStateMachine.AbstractStateMachineBuilder<NestedStateMachineBuilder<CTX, PARENT_CTX>,
                    NestedStateMachine<CTX, PARENT_CTX>, CTX> {
        private String completionPolicy = "Default Completion Policy";

        public NestedStateMachineBuilder<CTX, PARENT_CTX> completionPolicy(String completionPolicy) {
            this.completionPolicy = completionPolicy;
            return this;
        }

        @Override
        public NestedStateMachineBuilder<CTX, PARENT_CTX> builderType() {
            return this;
        }

        public NestedStateMachine<CTX, PARENT_CTX> build() {
            return new NestedStateMachine<>(this);
        }
    }
}