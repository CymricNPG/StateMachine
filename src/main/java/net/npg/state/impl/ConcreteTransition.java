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
import net.npg.state.Transition;

import java.util.Objects;
import java.util.function.BooleanSupplier;

public record ConcreteTransition<I extends Identifier>(
        I id,
        State<I> source,
        State<I> target,
        BooleanSupplier guard
) implements Transition<I> {

    public ConcreteTransition {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(source, "source must not be null");
        Objects.requireNonNull(target, "target must not be null");
        Objects.requireNonNull(guard, "guard must not be null");
    }

    @Override
    public boolean canTraverse() {
        return guard.getAsBoolean();
    }

    @Override
    public String toString() {
        return "ConcreteTransition{" +
                "id=" + id +
                '}';
    }
}
