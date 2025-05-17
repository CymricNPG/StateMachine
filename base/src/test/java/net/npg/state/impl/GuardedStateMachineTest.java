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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuardedStateMachineTest {

    @Test
    void testExecuteStateMachine_success() {
        final var state1 = new ConcreteState<>(new SimpleIdentifier("state1"));
        final var state2 = new ConcreteState<>(new SimpleIdentifier("state2"));

        final var model = new ConcreteStateModel<>(new SimpleIdentifier("model_id1"));
        model.addState(state1);
        model.addState(state2);
        model.addTransition(state1, state2, () -> true, new SimpleIdentifier("trans_id1"));
        final var token = new ConcreteToken<>(state1, model);
        final var stateMachine = new GuardedStateMachine<SimpleIdentifier>();

        final var resultToken = stateMachine.execute(token);

        assertEquals(state2, resultToken.state());
    }

    @Test
    void testExecuteWithNoTransition_fail() {
        final var state1 = new ConcreteState<>(new SimpleIdentifier("state1"));
        final var state2 = new ConcreteState<>(new SimpleIdentifier("state2"));

        final var model = new ConcreteStateModel<>(new SimpleIdentifier("model_id1"));
        model.addState(state1);
        model.addState(state2);
        model.addTransition(state1, state2, () -> false, new SimpleIdentifier("trans_id1"));
        final var token = new ConcreteToken<>(state1, model);
        final var stateMachine = new GuardedStateMachine<SimpleIdentifier>();
        final var resultToken = stateMachine.execute(token);

        assertEquals(state1, resultToken.state());
    }
}
