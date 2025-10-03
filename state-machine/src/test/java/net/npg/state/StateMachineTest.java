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

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static net.npg.state.Ids.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StateMachineTest {

    @Test
    void testExecuteStateMachine_success() {
        final var model = new StateModel<>(MODEL_ID);
        final var state1 = model.addState(ID1);
        final var state2 = model.addState(ID2);
        model.addTransition(state1, state2, () -> true, TRANS_ID);
        final var token = new Token<>(state1, model);

        final var resultToken = StateMachine.execute(token);

        assertEquals(state2, resultToken.state());
    }

    @Test
    void testExecuteObjectStateMachine_success() {
        final var model = new StateModel<>(MODEL_ID);
        final var state1 = model.addState(ID1);
        final var state2 = model.addState(ID2);
        model.addTransition(state1, state2, () -> true, TRANS_ID);
        final var sut = new StateMachine<>(model, state1);

        assertEquals(state2, sut.execute());
    }

    @Test
    void testExecuteWithNoTransition_fail() {

        final var model = new StateModel<>(MODEL_ID);
        final var state1 = model.addState(ID1);
        final var state2 = model.addState(ID2);
        model.addTransition(state1, state2, () -> false, TRANS_ID);
        final var token = new Token<>(state1, model);
        final var resultToken = StateMachine.execute(token);

        assertEquals(state1, resultToken.state());
    }

    @Test
    void testStateListenerCalled() {
        final var model = new StateModel<>(MODEL_ID);
        final var state1 = model.addState(ID1);
        final var called = new AtomicBoolean(false);
        final var state2 = model.addState(ID2, (s) -> called.set(true));
        model.addTransition(state1, state2, () -> true, TRANS_ID);
        new StateMachine<>(model, state1).execute();
        assertTrue(called.get());
    }

    @Test
    void testStateListenerCalled_withException() {
        final var model = new StateModel<>(MODEL_ID);
        final var state1 = model.addState(ID1);
        final var state2 = model.addState(ID2, (s) -> {
            throw new RuntimeException("ignore");
        });
        model.addTransition(state1, state2, () -> true, TRANS_ID);
        final var nextState = new StateMachine<>(model, state1).execute();
        assertEquals(state2, nextState);
    }
}