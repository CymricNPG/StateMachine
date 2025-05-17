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

import static org.junit.jupiter.api.Assertions.*;

class ConcreteTransitionTest {
    private static final SimpleIdentifier TRANS_ID = new SimpleIdentifier("trans_id1");

    @Test
    void testConcreteTransitionCreation_success() {
        final var state1 = new ConcreteState<>(new SimpleIdentifier("state1"));
        final var state2 = new ConcreteState<>(new SimpleIdentifier("state2"));

        final var transition = new ConcreteTransition<>(TRANS_ID, state1, state2, () -> true);
        assertEquals(state1, transition.source());
        assertEquals(state2, transition.target());
        assertTrue(transition.canTraverse());
    }

    @Test
    void testConcreteTransitionWithFailingGuard_fail() {
        final var state1 = new ConcreteState<>(new SimpleIdentifier("state1"));
        final var state2 = new ConcreteState<>(new SimpleIdentifier("state2"));

        final var transition = new ConcreteTransition<>(TRANS_ID, state1, state2, () -> false);
        assertFalse(transition.canTraverse());
    }

    @Test
    void testConcreteTransitionWithNullSource_fail() {
        final var target = new ConcreteState<>(new SimpleIdentifier("state2"));
        assertThrows(NullPointerException.class, () -> new ConcreteTransition<>(TRANS_ID, null, target, () -> true));
    }

    @Test
    void testConcreteTransitionWithNullGuard_fail() {
        final var source = new ConcreteState<>(new SimpleIdentifier("state1"));
        final var target = new ConcreteState<>(new SimpleIdentifier("state2"));
        assertThrows(NullPointerException.class, () -> new ConcreteTransition<>(TRANS_ID, source, target, null));
    }
}
