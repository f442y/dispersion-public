package com.github.f442y.dispersion.core.statemachine.events;

public sealed interface StateEvent extends StateMachineEvent permits ActionEvent, TransitionEvent {
}
