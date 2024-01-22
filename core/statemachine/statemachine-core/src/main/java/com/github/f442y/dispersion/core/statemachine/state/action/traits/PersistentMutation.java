package com.github.f442y.dispersion.core.statemachine.state.action.traits;

import com.github.f442y.dispersion.core.statemachine.state.action.ActionWithCallableTrigger;

/**
 * Trait interface to represent a persistent change in the system.
 * <p>
 * Changes which remain even if the system is restarted are considered persistent, an example would be a permanent
 * database update or write.
 * <p>
 * This interface is implemented to an {@link ActionWithCallableTrigger} representing
 * such changes, this has a primary function to allow the state observation to also persist the current state, if the
 * current state is not persisted with the {@link PersistentMutation} then on recovery (System Restart) the change
 * may be run/attempted again (Multiple Mutation).
 */
public interface PersistentMutation {}
