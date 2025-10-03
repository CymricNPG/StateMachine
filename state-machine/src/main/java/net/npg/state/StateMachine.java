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
import java.util.logging.Level;
import java.util.logging.Logger;

/// Represents the core execution engine for state machines, responsible for processing
/// transitions between states based on the current token context.
///
/// This class provides the primary `execute` method that processes state transitions
/// according to the defined rules. It uses the [Token] to track the current state
/// and model, and ensures transitions are executed safely with validation.
///
/// Classes in this package are designed to be used as the central control logic
/// for state machine execution.
///
/// @param <I> The type of the state identifier (e.g., String, Integer)
/// @see Token
/// @see Transition
/// @see StateModel
public class StateMachine<I> {
    private static final Logger LOGGER = Logger.getLogger(StateMachine.class.getName());

    private Token<I> token;

    public StateMachine(final StateModel<I> stateModel, final State<I> startState) {
        Objects.requireNonNull(stateModel, "stateModel must not be null");
        Objects.requireNonNull(startState, "startState must not be null");
        token = stateModel.createToken(startState);
    }

    ///  Executes the state machine from the current state, processing enabled transitions
    ///      until a state is reached which has no enabled transitions.
    ///
    /// @return The final state after all enabled transitions have been processed
    /// @throws IllegalStateException if multiple transitions are enabled from the same state
    public State<I> execute() {
        assert token != null;
        token = execute(token);
        return token.state();
    }


    /// Executes the state machine from the given token, processing enabled transitions
    /// until a state is reached which has no enabled transitions.
    ///
    /// This method:
    /// 1. Validates the input token
    /// 2. Continuously checks for enabled transitions from the current state
    /// 3. Processes the first enabled transition, updating the token's state
    /// 4. Returns when no more transitions are enabled
    /// 5. Throws an exception if multiple transitions are enabled simultaneously
    ///
    /// @param token The starting point for execution, containing the current state and model
    /// @return The final token after all enabled transitions have been processed
    /// @throws IllegalStateException if multiple transitions are enabled from the same state
    /// @throws NullPointerException  if the input token is null
    public static <I> Token<I> execute(final Token<I> token) {
        Objects.requireNonNull(token, "token cannot be null");
        if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.finest("Starting execution from state: " + token.state());
        }
        var workToken = token;
        while (true) {
            // Check for enabled transitions
            final var enabledTransitions = workToken.state().outgoingTransitions()
                    .stream()
                    .filter(Transition::canTraverse)
                    .toList();

            if (enabledTransitions.isEmpty()) {
                if (LOGGER.isLoggable(Level.FINEST)) {
                    LOGGER.finest("No currently enabled transitions from state: " + workToken.state());
                }
                return workToken;
            }
            if (enabledTransitions.size() > 1) {
                throw new IllegalStateException("Multiple transitions enabled from state: " + workToken.state());
            }
            final var enabledTransition = enabledTransitions.getFirst();
            if (LOGGER.isLoggable(Level.FINEST)) {
                LOGGER.finest("Transition found from state " + workToken.state() + " : " + enabledTransition);
            }
            final var newState = enabledTransition.target();
            workToken = workToken.update(newState);
            if (LOGGER.isLoggable(Level.FINEST)) {
                LOGGER.finest("Moved to state: " + workToken.state());
            }
            try {
                newState.stateListener().ifPresent(l -> l.accept(newState));
            } catch (final Exception e) {
                LOGGER.log(Level.WARNING, "Caught exception while processing state transition: " + workToken.state(), e);
            }
        }
    }
}