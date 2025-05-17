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

import static net.npg.state.Ids.*;
import static org.junit.jupiter.api.Assertions.*;

class TransitionTest {

    @Test
    void testTransitionCreation_success() {

        final var transition = new Transition<>(TRANS_ID, STATE1, STATE2, () -> true);
        assertEquals(STATE1, transition.source());
        assertEquals(STATE2, transition.target());
        assertTrue(transition.canTraverse());
    }

    @Test
    void testTransitionWithFailingGuard_fail() {
        final var transition = new Transition<>(TRANS_ID, STATE1, STATE2, () -> false);
        assertFalse(transition.canTraverse());
    }

    @Test
    void testTransitionWithNullSource_fail() {
        final var target = new State<>(ID1);
        assertThrows(NullPointerException.class, () -> new Transition<>(TRANS_ID, null, target, () -> true));
    }

    @Test
    void testTransitionWithNullGuard_fail() {
        final var source = new State<>(ID1);
        final var target = new State<>(ID2);
        assertThrows(NullPointerException.class, () -> new Transition<>(TRANS_ID, source, target, null));
    }
}
