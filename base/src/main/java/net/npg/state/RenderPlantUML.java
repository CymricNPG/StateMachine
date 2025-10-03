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

import java.util.Collection;
import java.util.stream.Collectors;

/// Utility class for generating PlantUML diagrams and markdown representations of state models.
/// This class provides static methods to convert [StateModel] instances into PlantUML syntax
/// and markdown-formatted PlantUML code blocks.
///
/// Classes in this package are designed to be used as utility classes. Instantiation is prevented
/// via the private constructor. All methods are static and operate on [StateModel] objects.
public final class RenderPlantUML {
    private RenderPlantUML() {
    }

    /// Generates a PlantUML diagram string representation of the provided state model.
    ///
    /// This method constructs a UML state diagram by:
    /// 1. Adding state nodes for each state in the model
    /// 2. Creating transition arrows between states with transition IDs as labels
    /// 3. Wrapping the diagram in PlantUML syntax (`@startuml`/`@enduml`)
    ///
    /// @param model The state model to convert to a diagram
    /// @return A string containing the PlantUML diagram syntax
    public static String generate(final StateModel<?> model) {
        final var diagram = new StringBuilder();
        diagram.append("@startuml").append("\n");

        model.states()
                .forEach(state -> diagram.append("state ").append(state.id()).append("\n"));
        final var transitions = model.states().stream()
                .map(State::outgoingTransitions)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        for (final var transition : transitions) {
            diagram.append(transition.source().id())
                    .append(" --> ")
                    .append(transition.target().id())
                    .append(" : ")
                    .append(transition.id())
                    .append("\n");
        }

        diagram.append("@enduml").append("\n");
        return diagram.toString();
    }

    /// Generates a markdown-formatted PlantUML diagram from the provided state model.
    ///
    /// This method:
    /// 1. Calls [#generate(StateModel)] to get the PlantUML syntax
    /// 2. Wraps the diagram in a markdown code block with the `plantuml` language identifier
    /// 3. Adds a header with the model's ID
    ///
    /// @param model The state model to convert to markdown
    /// @return A markdown string containing the PlantUML diagram
    public static String generateMarkdown(final StateModel<?> model) {
        final var uml = generate(model);
        return "# " + model.id() + "\n" +
                "```plantuml" + "\n" +
                uml +
                "``" + "\n";
    }
}