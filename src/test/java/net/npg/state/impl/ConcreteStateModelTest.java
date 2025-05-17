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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteStateModelTest {
    private static final SimpleIdentifier MODEL_ID = new SimpleIdentifier("model_id1");

    @Test
    void testCreateEmptyStateModel_success() {
        final var stateModel = new ConcreteStateModel<>(MODEL_ID);
        assertTrue(stateModel.states().isEmpty());
        assertTrue(stateModel.transitions().isEmpty());
    }

    @Test
    void testAddState_success() {
        final var stateModel = new ConcreteStateModel<>(MODEL_ID);
        final var state = new ConcreteState<>(new SimpleIdentifier("state1"));
        stateModel.addState(state);

        assertTrue(stateModel.states().contains(state));
    }

    @Test
    void testAddTransition_success() {
        final var stateModel = new ConcreteStateModel<>(MODEL_ID);
        final var state1 = new ConcreteState<>(new SimpleIdentifier("state1"));
        final var state2 = new ConcreteState<>(new SimpleIdentifier("state2"));

        stateModel.addState(state1);
        stateModel.addState(state2);

        final var guard = (BooleanSupplier) () -> true;
        stateModel.addTransition(state1, state2, guard, new SimpleIdentifier("t1"));

        assertEquals(1, stateModel.transitions().size());
        assertFalse(state1.outgoingTransitions().isEmpty());
        assertFalse(state2.incomingTransitions().isEmpty());
    }

    @Test
    void testAddNullState_fail() {
        final var stateModel = new ConcreteStateModel<>(MODEL_ID);
        assertThrows(NullPointerException.class, () -> stateModel.addState(null));
    }

    @Test
    void testAddStateFail() {
        final ConcreteStateModel<SimpleIdentifier> stateModel = new ConcreteStateModel<>(MODEL_ID);

        Assertions.assertThrows(NullPointerException.class, () -> stateModel.addState(null));
    }

    @Test
    void testAddTransitionFail() {
        final ConcreteStateModel<SimpleIdentifier> stateModel = new ConcreteStateModel<>(MODEL_ID);

        Assertions.assertThrows(NullPointerException.class,
                () -> stateModel.addTransition(null, null, () -> true, null));
    }
}
