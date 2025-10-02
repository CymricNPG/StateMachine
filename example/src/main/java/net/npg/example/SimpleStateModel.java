package net.npg.example;

import net.npg.state.*;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class SimpleStateModel {
    private static final Logger LOGGER = Logger.getLogger(SimpleStateModel.class.getName());

    static void main(final String[] args) {
        final var model = new StateModel<>("State Diagram");

        final var newState = model.addState("new");
        final var waiting = model.addState("waiting");
        final var searching = model.addState("searching");
        final var following = model.addState("following");
        final var finished = model.addState("finished");

        final var found = new AtomicBoolean(false);
        final var caught = new AtomicBoolean(false);
        final var startSearch = new AtomicBoolean(false);

        model.addTransition(newState, waiting, () -> true, "start");
        model.addTransition(waiting, searching, startSearch::get, "search");
        model.addTransition(waiting, finished, found::get, "found");
        model.addTransition(searching, following, caught::get, "follow");
        model.addTransition(following, waiting, () -> true, "searchagain");

        LOGGER.info("\n" + RenderPlantUML.generateMarkdown(model));

        final var token = model.createToken(newState);

        final var machine = new StateMachine<String>();
        final var newToken = machine.execute(token);
        checkState(newToken, waiting);
        startSearch.set(true);
        found.set(false);
        final var foundToken = machine.execute(newToken);
        checkState(foundToken, searching);
        found.set(false);
        final var followToken = machine.execute(foundToken);
        checkState(followToken, following);
    }

    private static void checkState(final Token<String> token, final State<String> expectedState) {
        if (!token.state().equals(expectedState)) {
            throw new IllegalStateException("Unexpected state " + token.state());
        }
    }
}