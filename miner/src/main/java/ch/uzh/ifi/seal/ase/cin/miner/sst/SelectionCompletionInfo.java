package ch.uzh.ifi.seal.ase.cin.miner.sst;

import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.commons.model.naming.types.ITypeName;
import cc.kave.commons.model.ssts.expressions.assignable.ICompletionExpression;
import cc.kave.commons.utils.ssts.completioninfo.ICompletionInfo;

public class SelectionCompletionInfo implements ICompletionInfo {
    private ICompletionExpression completionExpr;
    private ITypeName triggeredType;
    private ITypeName expectedType;
    private MethodName selection;

    public ICompletionExpression getCompletionExpr() {
        return completionExpr;
    }

    public void setCompletionExpr(ICompletionExpression completionExpr) {
        this.completionExpr = completionExpr;
    }

    public ITypeName getTriggeredType() {
        return triggeredType;
    }

    @Override
    public boolean hasExpectedType() {
        return expectedType != null;
    }

    public void setTriggeredType(ITypeName triggeredType) {
        this.triggeredType = triggeredType;
    }

    public ITypeName getExpectedType() {
        return expectedType;
    }

    public void setExpectedType(ITypeName expectedType) {
        this.expectedType = expectedType;
    }

    public MethodName getSelection() {
        return selection;
    }

    public void setSelection(MethodName selection) {
        this.selection = selection;
    }
}
