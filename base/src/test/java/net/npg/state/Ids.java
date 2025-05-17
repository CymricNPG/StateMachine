package net.npg.state;

public final class Ids {
    private Ids() {
    }

    static final SimpleIdentifier ID1 = new SimpleIdentifier("state1");
    static final SimpleIdentifier ID2 = new SimpleIdentifier("state2");
    static final SimpleIdentifier MODEL_ID = new SimpleIdentifier("model_id1");
    static final SimpleIdentifier TRANS_ID = new SimpleIdentifier("trans_id1");
    static final State<SimpleIdentifier> STATE1 = new State<>(ID1);
    static final State<SimpleIdentifier> STATE2 = new State<>(ID2);
}
