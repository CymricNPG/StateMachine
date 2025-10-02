package net.npg.state;

public final class RenderPlantUML {
    private RenderPlantUML() {
    }

    public static String generate(final StateModel<?> model) {
        final var diagram = new StringBuilder();
        diagram.append("@startuml").append("\n");

        model.states()
                .forEach(state -> diagram.append("state ").append(state.id()).append("\n"));

        for (final Transition<?> transition : model.transitions()) {
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

    public static String generateMarkdown(final StateModel<?> model) {
        final var uml = generate(model);
        return "# " + model.id() + "\n" +
                "```plantuml" + "\n" +
                uml +
                "``" + "\n";
    }
}