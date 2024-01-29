package com.github.f442y.dispersion.core.statemachine.context;

public class DefaultContexts {
    public static final EmptyStaticContextFactory EMPTY_STATIC_CONTEXT_FACTORY = new EmptyStaticContextFactory();
    private static final EmptyStaticContext EMPTY_STATIC_CONTEXT = new EmptyStaticContext();

    private DefaultContexts() {
        // no-op: Utility Class
    }

    public record EmptyStaticContext() implements StateMachineContext {}

    public record EmptyStaticContextFactory() implements StateMachineContextFactory<EmptyStaticContext> {
        @Override
        public EmptyStaticContext newInstance() {
            return EMPTY_STATIC_CONTEXT;
        }
    }
}