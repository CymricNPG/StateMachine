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

package net.npg.state.impl;

import net.npg.state.State;
import net.npg.state.Transition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public record ConcreteState<I>(
        I id,
        Collection<Transition<I>> outgoingTransitions,
        Collection<Transition<I>> incomingTransitions
) implements State<I> {
    public ConcreteState(final I id) {
        this(Objects.requireNonNull(id), new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public void addOutgoingTransition(final Transition<I> transition) {
        outgoingTransitions.add(Objects.requireNonNull(transition));
    }

    @Override
    public void addIncomingTransition(final Transition<I> transition) {
        incomingTransitions.add(Objects.requireNonNull(transition));
    }

    @Override
    public String toString() {
        return "ConcreteState{" +
                "id=" + id +
                '}';
    }
}
