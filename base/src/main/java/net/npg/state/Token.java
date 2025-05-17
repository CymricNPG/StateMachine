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

public record Token<I>(State<I> state, StateModel<I> model) {
    public Token {
        Objects.requireNonNull(state, "state cannot be null");
        Objects.requireNonNull(model, "model cannot be null");
    }

    public Token<I> update(final State<I> newState) {
        Objects.requireNonNull(newState);
        if (!model.states().contains(newState)) {
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
