package ch.uzh.ifi.seal.ase.cin.miner.sst;

import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.commons.model.ssts.impl.expressions.assignable.CompletionExpression;

public class SelectionCompletionExpression extends CompletionExpression {
    private MethodName selection;

    public SelectionCompletionExpression(MethodName selection) {
        this.selection = selection;
    }

    public MethodName getSelection() {
        return selection;
    }
}
