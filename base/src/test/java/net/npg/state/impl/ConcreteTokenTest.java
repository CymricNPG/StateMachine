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

import net.npg.state.State;
import net.npg.state.StateModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConcreteTokenTest {

    /**
     * Tests the update functionality of the ConcreteToken class.
     * Ensures that the token can update its state with a valid new state.
     * Also validates that exceptions are thrown for invalid cases such as
     * null new state or a state not part of the model.
     */

    @Test
    void testUpdateWithValidState() {
        // Arrange
        final State<String> initialState = mock(State.class);
        final State<String> newState = mock(State.class);
        final StateModel<String> model = mock(StateModel.class);

        when(model.states()).thenReturn(List.of(initialState, newState));

        final ConcreteToken<String> token = new ConcreteToken<>(initialState, model);

        // Act
        final var updatedToken = token.update(newState);

        // Assert
        assertNotNull(updatedToken);
        assertEquals(newState, updatedToken.state());
        assertSame(model, updatedToken.model());
    }

    @Test
    void testUpdateWithStateNotInModel() {
        // Arrange
        final State<String> initialState = mock(State.class);
        final State<String> newState = mock(State.class);
        final StateModel<String> model = mock(StateModel.class);

        when(model.states()).thenReturn(List.of(initialState));

        final ConcreteToken<String> token = new ConcreteToken<>(initialState, model);

        // Act & Assert
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> token.update(newState));
        assertEquals("State " + newState + " not part of model " + model, exception.getMessage());
    }

    @Test
    void testUpdateWithNullState() {
        // Arrange
        final State<String> initialState = mock(State.class);
        final StateModel<String> model = mock(StateModel.class);

        when(model.states()).thenReturn(List.of(initialState));

        final ConcreteToken<String> token = new ConcreteToken<>(initialState, model);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> token.update(null));
    }

    @Test
    void testToString() {
        final var state = new ConcreteState<>(new SimpleIdentifier("state1"));
        final var model = new ConcreteStateModel<>(new SimpleIdentifier("model_id1"));
        final var token = new ConcreteToken<>(state, model);
        assertTrue(token.toString().contains("state1"), "Token toString should contain state name:" + token);
    }
}
