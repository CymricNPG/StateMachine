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

public final class Ids {
    private Ids() {
    }

    static final SimpleIdentifier ID1 = new SimpleIdentifier("state1");
    static final SimpleIdentifier ID2 = new SimpleIdentifier("state2");
    static final SimpleIdentifier MODEL_ID = new SimpleIdentifier("model_id1");
    static final SimpleIdentifier MODEL_ID2 = new SimpleIdentifier("model_id2");
    static final SimpleIdentifier TRANS_ID = new SimpleIdentifier("trans_id1");
    static final State<SimpleIdentifier> STATE1 = new State<>(ID1);
    static final State<SimpleIdentifier> STATE2 = new State<>(ID2);
}