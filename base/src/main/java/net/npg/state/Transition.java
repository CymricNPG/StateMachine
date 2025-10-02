/*
 * Copyright (C) 2025 Roland Spatzenegger
 * This file is part of StateMachine.
 *
 * StateMachine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * StateMachine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with StateMachine. If not, see <https://www.gnu.org/licenses/>.
 */

package net.npg.state;

import java.util.Objects;
import java.util.function.BooleanSupplier;

/// Represents a transition between states in a state machine, encapsulating the transition's
/// identifier, source and target states, and a guard condition that determines when the
/// transition is allowed.
///
/// This record is part of the StateMachine framework and follows the GNU Lesser General
/// Public License (LGPL) v3 or later. It provides immutable access to transition properties
/// and includes validation for all fields.
///
/// @param <I> The type of the state identifier (e.g., String, Integer)
/// @see State
/// @see StateModel
public record Transition<I>(
        I id,
        State<I> source,
        State<I> target,
        BooleanSupplier guard
) {
    public Transition {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(source, "source must not be null");
        Objects.requireNonNull(target, "target must not be null");
        Objects.requireNonNull(guard, "guard must not be null");
    }

    /// Determines if the transition can be traversed based on the guard condition.
    ///
    /// This method evaluates the [BooleanSupplier] associated with the transition
    /// to check if the transition is allowed. The result is determined by calling
    /// [BooleanSupplier#getAsBoolean()].
    ///
    /// @return `true` if the transition is allowed, `false` otherwise
    boolean canTraverse() {
        return guard.getAsBoolean();
    }

    @Override
    public String toString() {
        return "Transition{" +
                "id=" + id +
                ", source=" + source.id() +
                ", target=" + target.id() +
                '}';
    }
}