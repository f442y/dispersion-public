package com.github.f442y.dispersion.core.statemachine.exception;

public abstract sealed class StateMachineException extends Exception permits ActionException, TransitionException {

    public StateMachineException(String message) {
        super(message);
    }

    public StateMachineException(String message, Throwable cause) {
        super(message, cause);
    }

    public StateMachineException(Throwable cause) {
        super(cause);
    }

    public StateMachineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
