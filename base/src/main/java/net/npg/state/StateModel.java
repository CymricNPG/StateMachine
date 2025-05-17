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

public record StateModel<I>(
        I id,
        Collection<State<I>> states,
        Collection<Transition<I>> transitions
) {

    public StateModel(final I id) {
        this(id, new ArrayList<>(), new ArrayList<>());
    }

    public State<I> addState(final I id) {
        final var state = new State<>(id);
        states.add(Objects.requireNonNull(state));
        return state;
    }

    public Transition<I> addTransition(final State<I> fromState, final State<I> toState, final BooleanSupplier guard, final I transitionId) {
        Objects.requireNonNull(fromState);
        Objects.requireNonNull(toState);
        Objects.requireNonNull(guard);
        Objects.requireNonNull(transitionId);
        final var transition = new Transition<>(transitionId, fromState, toState, guard);
        fromState.addOutgoingTransition(transition);
        toState.addIncomingTransition(transition);
        transitions().add(transition);
        return transition;
    }

    @Override
    public String toString() {
        return "StateModel{" +
                "id=" + id +
                '}';
    }
}
