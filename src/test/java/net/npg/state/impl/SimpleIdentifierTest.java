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

class SimpleIdentifierTest {

    @Test
    void testSimpleIdentifierCreation_success() {
        final var id = new SimpleIdentifier("id1");
        assertEquals("id1", id.id());
    }

    @Test
    void testSimpleIdentifierEquality_success() {
        final var id1 = new SimpleIdentifier("id1");
        final var id2 = new SimpleIdentifier("id1");
        assertEquals(id1, id2);
    }

    @Test
    void testSimpleIdentifierEquality_fail() {
        final var id1 = new SimpleIdentifier("id1");
        final var id2 = new SimpleIdentifier("id2");
        assertNotEquals(id1, id2);
    }

    @Test
    void testSimpleIdentifierWithNullId_fail() {
        assertThrows(NullPointerException.class, () -> new SimpleIdentifier(null));
    }
}
