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
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/// Represents a state machine model, encapsulating its unique identifier, states, and transitions.
/// This record serves as the core structure for defining state machines, providing methods to
/// add states and transitions while ensuring null safety and validation.
///
/// Classes in this package are designed to be used as immutable data containers. The
/// `StateModel` record ensures null safety for all fields and provides utility methods
/// for managing state and transition relationships.
///
/// @param <I>    The type of the state identifier (e.g., String, Integer)
/// @param states a collection of all states in this model
/// @param id     a unique identifier
/// @see State
/// @see Transition
/// @see Token
public record StateModel<I>(
        I id,
        Collection<State<I>> states
) {
    /// Constructs a new state model with the specified identifier and empty state/transition collections.
    ///
    /// This constructor initializes the model with the given ID and empty collections for states
    /// and transitions.
    ///
    /// @param id The unique identifier for the state model
    /// @throws NullPointerException if the provided ID is null
    public StateModel(final I id) {
        this(id, new ArrayList<>());
    }

    /// Ensure that all fields are set
    public StateModel {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(states, "states must not be null");
    }

    /// Adds a new state to this state model.
    ///
    /// This method creates a new [State] with the specified ID, ensures it is non-null,
    /// and adds it to the model's state collection. The state is returned for further configuration.
    ///
    /// @param id The identifier for the new state
    /// @return The newly created state
    /// @throws NullPointerException if the provided ID is null
    public State<I> addState(final I id) {
        Objects.requireNonNull(id, "id must not be null");
        checkStateIdExists(id);
        final var state = new State<>(id);
        states.add(state);
        return state;
    }

    /// Adds a new state to this state model.
    ///
    /// This method creates a new [State] with the specified ID, ensures it is non-null,
    /// and adds it to the model's state collection. The state is returned for further configuration.
    ///
    /// @param id            The identifier for the new state
    /// @param stateListener a stateListener which is called when a token reaches this state
    /// @return The newly created state
    /// @throws NullPointerException if the provided ID is null
    public State<I> addState(final I id, final Consumer<State<I>> stateListener) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(stateListener, "stateListener must not be null");
        checkStateIdExists(id);
        final var state = new State<>(id, stateListener);
        states.add(state);
        return state;
    }
    
    private void checkStateIdExists(final I id) {
        if (states.stream().map(State::id).anyMatch(x -> x.equals(id))) {
            throw new IllegalArgumentException("State with id " + id + " already exists");
        }
    }


    /// Adds a new transition between two states in this model.
    ///
    /// This method:
    /// 1. Validates that all parameters are non-null
    /// 2. Creates a new [Transition] with the specified properties
    /// 3. Links the transition to the source and target states
    /// 4. Adds the transition to the model's transition collection
    ///
    /// @param fromState    The source state of the transition
    /// @param toState      The target state of the transition
    /// @param guard        A [BooleanSupplier] that determines if the transition is allowed
    /// @param transitionId The unique identifier for the transition
    /// @return The newly created transition
    /// @throws NullPointerException if any parameter is null
    public Transition<I> addTransition(final State<I> fromState, final State<I> toState, final BooleanSupplier guard, final I transitionId) {
        Objects.requireNonNull(fromState, "fromState must not be null");
        Objects.requireNonNull(toState, "toState must not be null");
        Objects.requireNonNull(guard, "guard must not be null");
        Objects.requireNonNull(transitionId, "transitionId must not be null");
        if (!contains(fromState) || !contains(toState)) {
            throw new IllegalArgumentException("Both states (" + fromState + " -> " + toState + ") must be from the current model: " + id);
        }
        checkTransitionIdExists(transitionId);
        final var transition = new Transition<>(transitionId, fromState, toState, guard);
        fromState.addOutgoingTransition(transition);
        toState.addIncomingTransition(transition);
        return transition;
    }

    private void checkTransitionIdExists(final I transitionId) {
        if (states().stream().flatMap(s -> s.outgoingTransitions().stream()).anyMatch(t -> t.id().equals(transitionId))) {
            throw new IllegalArgumentException("Transition with id " + transitionId + " already exists");
        }
        // should be not necessary, all incoming transitions should have exactly one outgoing transition
        if (states().stream().flatMap(s -> s.incomingTransitions().stream()).anyMatch(t -> t.id().equals(transitionId))) {
            throw new IllegalArgumentException("Transition with id " + transitionId + " already exists");
        }
    }

    /// Creates an initial [Token] starting at a given state in this [StateModel]
    ///
    /// @param startState an existing state in this model
    /// @return a newly created [Token]
    public Token<I> createToken(final State<I> startState) {
        Objects.requireNonNull(startState, "startState must not be null");
        if (!contains(startState)) {
            throw new IllegalArgumentException("model must contain state " + startState);
        }
        return new Token<>(startState, this);
    }

    /// Checks if the specified state is part of this model.
    ///
    /// This method is used to validate state existence before updating tokens or
    /// performing operations that require state-to-model association.
    ///
    /// @param state The state to check for membership in this model
    /// @return `true` if the state is part of the model, `false` otherwise
    public boolean contains(final State<I> state) {
        return states.contains(state);
    }

    @Override
    public String toString() {
        return "StateModel{" +
                "id=" + id +
                '}';
    }

}