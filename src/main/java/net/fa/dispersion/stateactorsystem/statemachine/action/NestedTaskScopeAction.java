package net.fa.dispersion.stateactorsystem.statemachine.action;

import lombok.extern.slf4j.Slf4j;
import net.fa.dispersion.stateactorsystem.executors.NestedTaskScope;
import net.fa.dispersion.stateactorsystem.statemachine.NestedStateMachine;
import net.fa.dispersion.stateactorsystem.statemachine.context.StateMachineContext;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

@Slf4j
public class NestedTaskScopeAction<PARENT_CTX extends StateMachineContext<PARENT_CTX>,
        NESTED_CTX extends StateMachineContext<NESTED_CTX>> implements
        ProgressingAction<PARENT_CTX> {
    private final NestedStateMachine<NESTED_CTX, PARENT_CTX>[] nestedStateMachines;
    private final ScopedValue<PARENT_CTX> PARENT_CONTEXT;
    private final String name;

    private NestedTaskScopeAction(Builder<PARENT_CTX, NESTED_CTX> builder) {
        this.nestedStateMachines = builder.nestedStateMachines.toArray(new NestedStateMachine[0]);
        this.name = builder.name;
        this.PARENT_CONTEXT = builder.PARENT_CONTEXT;
    }

    @Override
    public PARENT_CTX progress(PARENT_CTX stateMachineContext) {
        log.trace("Thread: {} | Nested task scope action run", Thread.currentThread().threadId());
        // the context of the parent state-machine is passed in as a scoped value to every child state-machine
        ScopedValue.runWhere(PARENT_CONTEXT, stateMachineContext, () -> {
            try (NestedTaskScope<NESTED_CTX, PARENT_CTX> nestedTaskScope = new NestedTaskScope<>(name)) {
                // Todo: manage results of each statemachine
                // Todo: rerun failed task
                nestedTaskScope.addStateMachinesActorTaskToFork(nestedStateMachines);
                // (block) wait for all statemachines to finish
                nestedTaskScope.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return stateMachineContext;
    }

    public static class Builder<PARENT_CTX extends StateMachineContext<PARENT_CTX>,
            NESTED_CTX extends StateMachineContext<NESTED_CTX>> {
        private final ArrayList<NestedStateMachine<?, PARENT_CTX>> nestedStateMachines = new ArrayList<>();
        private String name;
        private ScopedValue<PARENT_CTX> PARENT_CONTEXT;

        public Builder<PARENT_CTX, NESTED_CTX> name(String name) {
            this.name = name;
            return this;
        }

        public Builder<PARENT_CTX, NESTED_CTX> addStateMachine(@NonNull NestedStateMachine<?, PARENT_CTX> stateMachine) {
            this.nestedStateMachines.add(stateMachine);
            return this;
        }

        public Builder<PARENT_CTX, NESTED_CTX> addScopedParentContext(ScopedValue<PARENT_CTX> scopedContext) {
            this.PARENT_CONTEXT = scopedContext;
            return this;
        }

        public NestedTaskScopeAction<PARENT_CTX, NESTED_CTX> build() {
            if (nestedStateMachines.size() < 2) {
                throw new IllegalStateException(STR."""
                                                
                                                                        
                        NestedTaskScopeAction Action must have at least two state machines.
                        current StateMachines added: [\{nestedStateMachines.size()}]
                        If only one StateMachine is needed, consider making it a part of the parent StateMachine
                        """);
            }

            if (PARENT_CONTEXT == null) {
                throw new IllegalStateException("""
                                                
                                                                        
                        Scoped Parent Context must be set
                        """);
            }
            return new NestedTaskScopeAction<>(this);
        }
    }
}
