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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteStateTest {
    private static final SimpleIdentifier TRANS_ID = new SimpleIdentifier("trans_id1");

    @Test
    void testConcreteStateCreation_success() {
        final var state = new ConcreteState<>(new SimpleIdentifier("state1"));
        assertNotNull(state.id());
        assertTrue(state.outgoingTransitions().isEmpty());
        assertTrue(state.incomingTransitions().isEmpty());
    }

    @Test
    void testAddOutgoingTransition_success() {
        final var state = new ConcreteState<>(new SimpleIdentifier("state1"));
        final var targetState = new ConcreteState<>(new SimpleIdentifier("state2"));
        final var transition = new ConcreteTransition<>(TRANS_ID, state, targetState, () -> true);

        state.addOutgoingTransition(transition);
        assertTrue(state.outgoingTransitions().contains(transition));
    }

    @Test
    void testAddIncomingTransition_success() {
        final var state = new ConcreteState<>(new SimpleIdentifier("state1"));
        final var sourceState = new ConcreteState<>(new SimpleIdentifier("state2"));
        final var transition = new ConcreteTransition<>(TRANS_ID, sourceState, state, () -> true);

        state.addIncomingTransition(transition);
        assertTrue(state.incomingTransitions().contains(transition));
    }

    @Test
    void testAddNullOutgoingTransition_fail() {
        final var state = new ConcreteState<>(new SimpleIdentifier("state1"));
        assertThrows(NullPointerException.class, () -> state.addOutgoingTransition(null));
    }
}