package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.ssts.IStatement;
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
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

public class EnclosingMethodTrackingVisitor extends AbstractTraversingNodeVisitor {

    private Map<ISSTNode, MethodProperties> propertiesMap = Maps.newHashMap();

    public MethodProperties getEnclosingMethodProperties(IStatement statement) {
        return propertiesMap.get(statement);
    }

    public MethodProperties getFirstOfClass(Class clazz) {
         Optional<Map.Entry<ISSTNode, MethodProperties>> entryOptional = propertiesMap.entrySet().stream()
                .filter(e -> e.getKey().getClass().equals(clazz))
                .findAny();

         if(!entryOptional.isPresent())
             return null;

         return entryOptional.get().getValue();
    }

    @Override
    public Object visit(IDelegateDeclaration stmt, Object o) {
        registerNode(stmt, o);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IEventDeclaration stmt, Object o) {
        registerNode(stmt, o);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IFieldDeclaration stmt, Object o) {
        registerNode(stmt, o);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IMethodDeclaration decl, Object o) {
        registerNode(decl, o);
        return super.visit(decl, o);
    }

    @Override
    public Object visit(IPropertyDeclaration decl, Object o) {
        registerNode(decl, o);
        return super.visit(decl, o);
    }

    @Override
    public Object visit(IVariableDeclaration stmt, Object o) {
        registerNode(stmt, o);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IAssignment stmt, Object o) {
        registerNode(stmt, o);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IBreakStatement stmt, Object o) {
        registerNode(stmt, o);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IContinueStatement stmt, Object o) {
        registerNode(stmt, o);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IEventSubscriptionStatement stmt, Object o) {
        registerNode(stmt, o);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IExpressionStatement stmt, Object o) {
        registerNode(stmt, o);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IGotoStatement stmt, Object o) {
        registerNode(stmt, o);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(ILabelledStatement stmt, Object o) {
        registerNode(stmt, o);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IReturnStatement stmt, Object o) {
        registerNode(stmt, o);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IThrowStatement stmt, Object o) {
        registerNode(stmt, o);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(IDoLoop block, Object o) {
        registerNode(block, o);
        return super.visit(block, o);
    }

    @Override
    public Object visit(IForEachLoop block, Object o) {
        registerNode(block, o);
        return super.visit(block, o);
    }

    @Override
    public Object visit(IForLoop block, Object o) {
        registerNode(block, o);
        return super.visit(block, o);
    }

    @Override
    public Object visit(IIfElseBlock block, Object o) {
        registerNode(block, o);
        return super.visit(block, o);
    }

    @Override
    public Object visit(ILockBlock stmt, Object o) {
        registerNode(stmt, o);
        return super.visit(stmt, o);
    }

    @Override
    public Object visit(ISwitchBlock block, Object o) {
        registerNode(block, o);
        return super.visit(block, o);
    }

    @Override
    public Object visit(ITryBlock block, Object o) {
        registerNode(block, o);
        return super.visit(block, o);
    }

    @Override
    public Object visit(IUncheckedBlock block, Object o) {
        registerNode(block, o);
        return super.visit(block, o);
    }

    @Override
    public Object visit(IUnsafeBlock block, Object o) {
        registerNode(block, o);
        return super.visit(block, o);
    }

    @Override
    public Object visit(IUsingBlock block, Object o) {
        registerNode(block, o);
        return super.visit(block, o);
    }

    @Override
    public Object visit(IWhileLoop block, Object o) {
        registerNode(block, o);
        return super.visit(block, o);
    }

    @Override
    public Object visit(ICompletionExpression entity, Object o) {
        registerNode(entity, o);
        return super.visit(entity, o);
    }

    @Override
    public Object visit(IComposedExpression expr, Object o) {
        registerNode(expr, o);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IIfElseExpression expr, Object o) {
        registerNode(expr, o);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IInvocationExpression expr, Object o) {
        registerNode(expr, o);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(ILambdaExpression expr, Object o) {
        registerNode(expr, o);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(ILoopHeaderBlockExpression expr, Object o) {
        registerNode(expr, o);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IConstantValueExpression expr, Object o) {
        registerNode(expr, o);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(INullExpression expr, Object o) {
        registerNode(expr, o);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IReferenceExpression expr, Object o) {
        registerNode(expr, o);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(ICastExpression expr, Object o) {
        registerNode(expr, o);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IIndexAccessExpression expr, Object o) {
        registerNode(expr, o);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(ITypeCheckExpression expr, Object o) {
        registerNode(expr, o);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IBinaryExpression expr, Object o) {
        registerNode(expr, o);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IUnaryExpression expr, Object o) {
        registerNode(expr, o);
        return super.visit(expr, o);
    }

    @Override
    public Object visit(IEventReference ref, Object o) {
        registerNode(ref, o);
        return super.visit(ref, o);
    }

    @Override
    public Object visit(IFieldReference ref, Object o) {
        registerNode(ref, o);
        return super.visit(ref, o);
    }

    @Override
    public Object visit(IMethodReference ref, Object o) {
        registerNode(ref, o);
        return super.visit(ref, o);
    }

    @Override
    public Object visit(IPropertyReference ref, Object o) {
        registerNode(ref, o);
        return super.visit(ref, o);
    }

    @Override
    public Object visit(IVariableReference ref, Object o) {
        registerNode(ref, o);
        return super.visit(ref, o);
    }

    @Override
    public Object visit(IIndexAccessReference ref, Object o) {
        registerNode(ref, o);
        return super.visit(ref, o);
    }

    @Override
    public Object visit(IUnknownReference ref, Object o) {
        registerNode(ref, o);
        return super.visit(ref, o);
    }

    @Override
    public Object visit(IUnknownExpression unknownExpr, Object o) {
        registerNode(unknownExpr, o);
        return super.visit(unknownExpr, o);
    }

    @Override
    public Object visit(IUnknownStatement unknownStmt, Object o) {
        registerNode(unknownStmt, o);
        return super.visit(unknownStmt, o);
    }

    private void registerNode(ISSTNode node, Object o) {
        if (o instanceof MethodProperties) {
            MethodProperties properties = (MethodProperties) o;
            propertiesMap.put(node, properties);

        }
    }
}
