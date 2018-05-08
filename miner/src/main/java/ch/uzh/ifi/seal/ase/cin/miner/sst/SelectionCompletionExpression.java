package ch.uzh.ifi.seal.ase.cin.miner.sst;

import cc.kave.commons.model.ssts.impl.expressions.assignable.CompletionExpression;

public class SelectionCompletionExpression extends CompletionExpression {
    private String selection;

    public SelectionCompletionExpression(String selection) {
        this.selection = selection;
    }

    public String getSelection() {
        return selection;
    }
}
