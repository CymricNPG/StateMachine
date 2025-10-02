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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.BooleanSupplier;

import static net.npg.state.Ids.*;
import static org.junit.jupiter.api.Assertions.*;

class StateModelTest {

    @Test
    void testCreateEmptyStateModel_success() {
        final var stateModel = new StateModel<>(MODEL_ID);
        assertTrue(stateModel.states().isEmpty());
        assertTrue(stateModel.transitions().isEmpty());
    }

    @Test
    void testAddState_success() {
        final var stateModel = new StateModel<>(MODEL_ID);
        final var state = stateModel.addState(ID1);
        assertTrue(stateModel.states().contains(state));
    }

    @Test
    void testCreateToken() {
        final var stateModel = new StateModel<>(MODEL_ID);
        final var state1 = stateModel.addState(ID1);
        final var token = stateModel.createToken(state1);
        assertEquals(stateModel, token.model());
        assertEquals(state1, token.state());
    }

    @Test
    void testCreateInvalidToken() {
        final var stateModel = new StateModel<>(MODEL_ID);
        final var state1 = new State<>(ID1);
        assertThrows(IllegalArgumentException.class, () -> stateModel.createToken(state1));
    }

    @Test
    void testAddTransition_success() {
        final var stateModel = new StateModel<>(MODEL_ID);
        final var state1 = stateModel.addState(ID1);
        final var state2 = stateModel.addState(ID2);

        final var guard = (BooleanSupplier) () -> true;
        stateModel.addTransition(state1, state2, guard, TRANS_ID);

        assertEquals(1, stateModel.transitions().size());
        assertFalse(state1.outgoingTransitions().isEmpty());
        assertFalse(state2.incomingTransitions().isEmpty());
    }

    @Test
    void testAddNullState_fail() {
        final var stateModel = new StateModel<>(MODEL_ID);
        assertThrows(NullPointerException.class, () -> stateModel.addState(null));
    }

    @Test
    void testAddStateFail() {
        final StateModel<SimpleIdentifier> stateModel = new StateModel<>(MODEL_ID);

        Assertions.assertThrows(NullPointerException.class, () -> stateModel.addState(null));
    }

    @Test
    void testAddTransitionFail() {
        final StateModel<SimpleIdentifier> stateModel = new StateModel<>(MODEL_ID);

        Assertions.assertThrows(NullPointerException.class,
                () -> stateModel.addTransition(null, null, () -> true, null));
    }
}