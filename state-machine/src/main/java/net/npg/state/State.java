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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/// Represents a state in a state machine, containing its identifier and transition relationships.
/// This record encapsulates the core properties of a state, including its unique identifier
/// and collections of outgoing/incoming transitions. It is part of the StateMachine framework
/// and follows the GNU Lesser General Public License (LGPL) v3 or later.
///
/// Classes in this package are designed to be used as immutable data containers. While
/// the record itself is immutable, the transition collections provide mutable operations
/// for adding transitions dynamically.
///
/// @param <I>                 The type of the state identifier (e.g., String, Integer)
/// @param id                  a unique identifier for this state
/// @param outgoingTransitions a collection of outgoing transitions
/// @param incomingTransitions a collection of incoming transitions
/// @see Transition
/// @see StateModel
public record State<I>(
        I id,
        Collection<Transition<I>> outgoingTransitions,
        Collection<Transition<I>> incomingTransitions
) {
    /// Constructs a new state with the specified identifier and empty transition collections.
    ///
    /// The resulting state has no outgoing or incoming transitions by default. Transitions
    /// can be added using the [#addOutgoingTransition(Transition)] and
    /// [#addIncomingTransition(Transition)] methods.
    ///
    /// @param id The unique identifier for the state
    /// @throws NullPointerException if the provided identifier is null
    State(final I id) {
        this(Objects.requireNonNull(id), new ArrayList<>(), new ArrayList<>());
    }

    /// Ensure that all fields are set
    public State {
        Objects.requireNonNull(id, "id cannot be null");
        Objects.requireNonNull(outgoingTransitions, "outgoingTransitions cannot be null");
        Objects.requireNonNull(incomingTransitions, "incomingTransitions cannot be null");
    }

    /// Adds an outgoing transition to this state.
    ///
    /// This method appends the specified transition to the list of outgoing transitions.
    /// The transition must not be null and start in this state.
    ///
    /// @param transition The outgoing transition to add
    /// @throws NullPointerException if the provided transition is null
    void addOutgoingTransition(final Transition<I> transition) {
        Objects.requireNonNull(transition, "Transition cannot be null");
        if (!equals(transition.source())) {
            throw new IllegalArgumentException("Transition " + transition + "  must start in this state:" + this);
        }
        outgoingTransitions.add(transition);
    }

    /// Adds an incoming transition to this state.
    ///
    /// This method appends the specified transition to the list of incoming transitions.
    /// The transition must not be null and end in this state.
    ///
    /// @param transition The incoming transition to add
    /// @throws NullPointerException if the provided transition is null
    void addIncomingTransition(final Transition<I> transition) {
        Objects.requireNonNull(transition, "Transition cannot be null");
        if (!equals(transition.target())) {
            throw new IllegalArgumentException("Transition " + transition + "  must end in this state:" + this);
        }
        incomingTransitions.add(transition);
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                '}';
    }
}