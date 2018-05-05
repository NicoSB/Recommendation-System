package ch.uzh.ifi.seal.ase.cin.recommender.util;

import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.blocks.*;
import cc.kave.commons.model.ssts.declarations.*;
import cc.kave.commons.model.ssts.expressions.assignable.*;
import cc.kave.commons.model.ssts.expressions.loopheader.ILoopHeaderBlockExpression;
import cc.kave.commons.model.ssts.expressions.simple.IConstantValueExpression;
import cc.kave.commons.model.ssts.expressions.simple.INullExpression;
import cc.kave.commons.model.ssts.expressions.simple.IReferenceExpression;
import cc.kave.commons.model.ssts.expressions.simple.IUnknownExpression;
import cc.kave.commons.model.ssts.impl.visitor.AbstractTraversingNodeVisitor;
import cc.kave.commons.model.ssts.references.*;
import cc.kave.commons.model.ssts.statements.*;
import cc.kave.commons.model.ssts.visitor.ISSTNode;

public class FindFirstVisitor extends AbstractTraversingNodeVisitor {

    private Class clazz;
    private ISSTNode result;

    public FindFirstVisitor(Class clazz) {
        this.clazz = clazz;
    }

    public ISSTNode getResult() {
        return result;
    }

    public void reset() {
        result = null;
    }

    @Override
    public Object visit(ISST sst, Object o) {
        checkMatch(sst);
        return super.visit(sst, o);
    }

    @Override
    public Object visit(IDelegateDeclaration stmt, Object o) {
        checkMatch(stmt);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IEventDeclaration stmt, Object o) {
        checkMatch(stmt);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IFieldDeclaration stmt, Object o) {
        checkMatch(stmt);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IMethodDeclaration decl, Object o) {
        checkMatch(decl);
        return super.visit(decl, o);
    }

    @Override
    public Object visit(IPropertyDeclaration decl, Object o) {
        checkMatch(decl);
        return super.visit(decl, o);
    }

    @Override
    public Object visit(IVariableDeclaration stmt, Object o) {
        checkMatch(stmt);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IAssignment stmt, Object o) {
        checkMatch(stmt);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IBreakStatement stmt, Object o) {
        checkMatch(stmt);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IContinueStatement stmt, Object o) {
        checkMatch(stmt);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IEventSubscriptionStatement stmt, Object o) {
        checkMatch(stmt);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IExpressionStatement stmt, Object o) {
        checkMatch(stmt);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IGotoStatement stmt, Object o) {
        checkMatch(stmt);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(ILabelledStatement stmt, Object o) {
        checkMatch(stmt);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IReturnStatement stmt, Object o) {
        checkMatch(stmt);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IThrowStatement stmt, Object o) {
        checkMatch(stmt);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IDoLoop block, Object o) {
        checkMatch(block);
        return super.visit(block, o);
    }

    @Override
    public Object visit(IForEachLoop block, Object o) {
        checkMatch(block);
        return super.visit(block, o);
    }

    @Override
    public Object visit(IForLoop block, Object o) {
        checkMatch(block);
        return super.visit(block, o);
    }

    @Override
    public Object visit(IIfElseBlock block, Object o) {
        checkMatch(block);
        return super.visit(block, o);
    }

    @Override
    public Object visit(ILockBlock stmt, Object o) {
        checkMatch(stmt);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(ISwitchBlock block, Object o) {
        checkMatch(block);
        return super.visit(block, o);
    }

    @Override
    public Object visit(ITryBlock block, Object o) {
        checkMatch(block);
        return super.visit(block, o);
    }

    @Override
    public Object visit(IUncheckedBlock block, Object o) {
        checkMatch(block);
        return super.visit(block, o);
    }

    @Override
    public Object visit(IUnsafeBlock block, Object o) {
        checkMatch(block);
        return super.visit(block, o);
    }

    @Override
    public Object visit(IUsingBlock block, Object o) {
        checkMatch(block);
        return super.visit(block, o);
    }

    @Override
    public Object visit(IWhileLoop block, Object o) {
        checkMatch(block);
        return super.visit(block, o);
    }

    @Override
    public Object visit(ICompletionExpression entity, Object o) {
        checkMatch(entity);
        return super.visit(entity, o);
    }

    @Override
    public Object visit(IComposedExpression expr, Object o) {
        checkMatch(expr);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IIfElseExpression expr, Object o) {
        checkMatch(expr);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IInvocationExpression expr, Object o) {
        checkMatch(expr);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(ILambdaExpression expr, Object o) {
        checkMatch(expr);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(ILoopHeaderBlockExpression expr, Object o) {
        checkMatch(expr);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IConstantValueExpression expr, Object o) {
        checkMatch(expr);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(INullExpression expr, Object o) {
        checkMatch(expr);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IReferenceExpression expr, Object o) {
        checkMatch(expr);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(ICastExpression expr, Object o) {
        checkMatch(expr);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IIndexAccessExpression expr, Object o) {
        checkMatch(expr);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(ITypeCheckExpression expr, Object o) {
        checkMatch(expr);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IBinaryExpression expr, Object o) {
        checkMatch(expr);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IUnaryExpression expr, Object o) {
        checkMatch(expr);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IEventReference ref, Object o) {
        checkMatch(ref);
        return super.visit(ref, o);
    }

    @Override
    public Object visit(IFieldReference ref, Object o) {
        checkMatch(ref);
        return super.visit(ref, o);
    }

    @Override
    public Object visit(IMethodReference ref, Object o) {
        checkMatch(ref);
        return super.visit(ref, o);
    }

    @Override
    public Object visit(IPropertyReference ref, Object o) {
        checkMatch(ref);
        return super.visit(ref, o);
    }

    @Override
    public Object visit(IVariableReference ref, Object o) {
        checkMatch(ref);
        return super.visit(ref, o);
    }

    @Override
    public Object visit(IIndexAccessReference ref, Object o) {
        checkMatch(ref);
        return super.visit(ref, o);
    }

    @Override
    public Object visit(IUnknownReference ref, Object o) {
        checkMatch(ref);
        return super.visit(ref, o);
    }

    @Override
    public Object visit(IUnknownExpression unknownExpr, Object o) {
        checkMatch(unknownExpr);
        return super.visit(unknownExpr, o);
    }

    @Override
    public Object visit(IUnknownStatement unknownStmt, Object o) {
        checkMatch(unknownStmt);
        return super.visit(unknownStmt, o);
    }

    private void checkMatch(ISSTNode node) {
        if (clazz.isInstance(node) && result == null)
            result = node;
    }
}
