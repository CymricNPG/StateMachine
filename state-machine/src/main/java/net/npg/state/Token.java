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

/// Represents a token in a state machine, linking a specific state to its associated model.
/// This record encapsulates the relationship between a [State] and its [StateModel],
/// providing immutable access to the state and model information.
///
/// Classes in this package are designed to be used as immutable data containers. The
/// `Token` record ensures null safety for both state and model fields, and provides
/// a method to update the state while validating its existence in the model.
///
/// @param <I>   The type of the state identifier (e.g., String, Integer)
/// @param state the current [State] of this [Token]
/// @param model the [StateModel] to which this token belongs
/// @see State
/// @see StateModel
public record Token<I>(State<I> state, StateModel<I> model) {

    /// Ensure that all fields are set
    public Token {
        Objects.requireNonNull(state, "state cannot be null");
        Objects.requireNonNull(model, "model cannot be null");
    }

    /// Returns a new `Token` instance with the updated state.
    ///
    /// This internal method creates a new `Token` using the same model but with the specified
    /// new state. It validates that the new state exists in the model's state collection.
    /// If the state is not found, an [IllegalArgumentException] is thrown.
    ///
    /// @param newState The new state to associate with this token
    /// @return A new `Token` instance with the updated state
    /// @throws IllegalArgumentException if the new state is not part of the model
    Token<I> update(final State<I> newState) {
        Objects.requireNonNull(newState);
        if (!model.contains(newState)) {
            throw new IllegalArgumentException("State " + newState + " not part of model " + model);
        }
        return new Token<>(newState, model);
    }

    @Override
    public String toString() {
        return "Token{" +
                "state=" + state +
                '}';
    }
}