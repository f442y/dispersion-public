package net.fa.dispersion.stateactorsystem.statemachine.action.io;

public sealed abstract class IOAction permits DatabaseAction, NetworkAction, FileAction {
}
