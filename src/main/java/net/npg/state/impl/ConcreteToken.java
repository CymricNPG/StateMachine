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
import net.npg.state.State;
import net.npg.state.StateModel;
import net.npg.state.Token;

import java.util.Objects;

public record ConcreteToken<I extends Identifier>(State<I> state, StateModel<I> model) implements Token<I> {
    public ConcreteToken {
        Objects.requireNonNull(state, "state cannot be null");
        Objects.requireNonNull(model, "model cannot be null");
    }

    @Override
    public Token<I> update(final State<I> newState) {
        Objects.requireNonNull(newState);
        if (!model.states().contains(newState)) {
            throw new IllegalArgumentException("State " + newState + " not part of model " + model);
        }
        return new ConcreteToken<>(newState, model);
    }

    @Override
    public String toString() {
        return "ConcreteToken{" +
                "state=" + state +
                '}';
    }
}
