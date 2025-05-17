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

import static net.npg.state.Ids.ID1;
import static net.npg.state.Ids.ID2;
import static org.junit.jupiter.api.Assertions.*;

class SimpleIdentifierTest {

    @Test
    void testSimpleIdentifierCreation_success() {
        assertEquals("id1", new SimpleIdentifier("id1").id());
    }

    @Test
    void testSimpleIdentifierEquality_success() {
        final var ids1 = new SimpleIdentifier("id1_");
        final var ids2 = new SimpleIdentifier("id1_");
        assertEquals(ids1, ids2);
    }

    @Test
    void testSimpleIdentifierEquality_fail() {
        assertNotEquals(ID1, ID2);
    }

    @Test
    void testSimpleIdentifierWithNullId_fail() {
        assertThrows(NullPointerException.class, () -> new SimpleIdentifier(null));
    }
}
