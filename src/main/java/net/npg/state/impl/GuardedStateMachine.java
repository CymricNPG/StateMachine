/*
 * Copyright (C) 2023 Roland Spatzenegger
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

package net.npg.state.impl;

import net.npg.state.Identifier;
import net.npg.state.StateMachine;
import net.npg.state.Token;
import net.npg.state.Transition;

import java.util.Objects;

public class GuardedStateMachine<I extends Identifier> implements StateMachine<I> {
    @Override
    public Token<I> execute(final Token<I> token) {
        Objects.requireNonNull(token);
        System.out.println("Starting execution from state: " + token.state());
        var workToken = token;
        while (true) {
            // Check for enabled transitions
            final var enabledTransitions = workToken.state().outgoingTransitions()
                    .stream()
                    .filter(Transition::canTraverse)
                    .toList();

            if (enabledTransitions.isEmpty()) {
                System.out.println("No more transitions enabled from state: " + workToken.state());
                return workToken;
            }
            if (enabledTransitions.size() > 1) {
                throw new IllegalStateException("Multiple transitions enabled from state: " + workToken.state());
            }
            final var enabledTransition = enabledTransitions.getFirst();
            System.out.println("Transition found from state " + workToken.state() + " : " + enabledTransition);
            final var newState = enabledTransition.target();
            workToken = workToken.update(newState);
            System.out.println("Moved to state: " + workToken.state());
        }
    }
}
