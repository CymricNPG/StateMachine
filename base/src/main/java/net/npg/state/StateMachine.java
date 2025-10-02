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

public class StateMachine<I> {
    private static final Logger LOGGER = Logger.getLogger(StateMachine.class.getName());

    public Token<I> execute(final Token<I> token) {
        Objects.requireNonNull(token);
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
        }
    }
}