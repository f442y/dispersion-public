package com.github.f442y.dispersion.core.statemachine.exception;


public non-sealed class TransitionException extends StateMachineException {
    public TransitionException(String message) {
        super(message);
    }

    public TransitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransitionException(Throwable cause) {
        super(cause);
    }

    public TransitionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
