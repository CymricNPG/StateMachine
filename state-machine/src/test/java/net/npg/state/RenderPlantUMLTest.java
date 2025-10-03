package net.npg.state;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RenderPlantUMLTest {

    @Test
    void generate() {
        final var model = new StateModel<>("nope");
        final var s1 = model.addState("State1");
        final var s2 = model.addState("State2");
        model.addTransition(s1, s2, () -> true, "trans");
        final var result = RenderPlantUML.generate(model);
        Assertions.assertTrue(result.contains("State1"), result);
        Assertions.assertTrue(result.contains("State2"), result);
        Assertions.assertTrue(result.contains("trans"), result);

    }
}