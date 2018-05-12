package ch.uzh.ifi.seal.ase.cin.miner.sst;

import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.commons.model.naming.types.ITypeName;
import cc.kave.commons.model.ssts.IReference;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.blocks.*;
import cc.kave.commons.model.ssts.declarations.*;
import cc.kave.commons.model.ssts.expressions.IAssignableExpression;
import cc.kave.commons.model.ssts.expressions.ILoopHeaderExpression;
import cc.kave.commons.model.ssts.expressions.ISimpleExpression;
import cc.kave.commons.model.ssts.expressions.assignable.*;
import cc.kave.commons.model.ssts.expressions.loopheader.ILoopHeaderBlockExpression;
import cc.kave.commons.model.ssts.expressions.simple.IConstantValueExpression;
import cc.kave.commons.model.ssts.expressions.simple.INullExpression;
import cc.kave.commons.model.ssts.expressions.simple.IReferenceExpression;
import cc.kave.commons.model.ssts.expressions.simple.IUnknownExpression;
import cc.kave.commons.model.ssts.impl.SST;
import cc.kave.commons.model.ssts.impl.blocks.*;
import cc.kave.commons.model.ssts.impl.declarations.*;
import cc.kave.commons.model.ssts.impl.expressions.assignable.*;
import cc.kave.commons.model.ssts.impl.expressions.loopheader.LoopHeaderBlockExpression;
import cc.kave.commons.model.ssts.impl.expressions.simple.ConstantValueExpression;
import cc.kave.commons.model.ssts.impl.expressions.simple.NullExpression;
import cc.kave.commons.model.ssts.impl.expressions.simple.ReferenceExpression;
import cc.kave.commons.model.ssts.impl.expressions.simple.UnknownExpression;
import cc.kave.commons.model.ssts.impl.references.*;
import cc.kave.commons.model.ssts.impl.statements.*;
import cc.kave.commons.model.ssts.impl.visitor.AbstractThrowingNodeVisitor;
import cc.kave.commons.model.ssts.references.*;
import cc.kave.commons.model.ssts.statements.*;
import cc.kave.commons.model.ssts.visitor.ISSTNode;
import ch.uzh.ifi.seal.ase.cin.recommender.recommendation.QueryExtractor;

import java.util.ArrayList;
import java.util.List;

public class InvocationToCompletionConvertingVisitor extends AbstractThrowingNodeVisitor<Void, ISSTNode> {
    private boolean hasCompletionExpression = false;

    public boolean hasCompletionExpression() {
        return hasCompletionExpression;
    }

    @Override
    public ISSTNode visit(ISST sst, Void context) {
        SST sstClone = new SST();
        sstClone.setEnclosingType(sst.getEnclosingType());
        sstClone.setPartialClassIdentifier(sst.getPartialClassIdentifier());
        for (IMethodDeclaration method : sst.getMethods()) {
            sstClone.getMethods().add((IMethodDeclaration) method.accept(this, context));
        }

        return sstClone;
    }

    @Override
    public ISSTNode visit(IInvocationExpression entity, Void context) {
        ITypeName type = entity.getMethodName().getDeclaringType();

        SelectionCompletionExpression completionExpression = new SelectionCompletionExpression((MethodName) entity.getMethodName());
        completionExpression.setTypeReference(type);

        hasCompletionExpression = true;

        return completionExpression;
    }


    @Override
    public ISSTNode visit(IDelegateDeclaration stmt, Void context) {
        DelegateDeclaration clone = new DelegateDeclaration();
        clone.setName(stmt.getName());
        return clone;
    }

    @Override
    public ISSTNode visit(IEventDeclaration stmt, Void context) {
        EventDeclaration clone = new EventDeclaration();
        clone.setName(stmt.getName());
        return clone;
    }

    @Override
    public ISSTNode visit(IFieldDeclaration stmt, Void context) {
        FieldDeclaration clone = new FieldDeclaration();
        clone.setName(stmt.getName());
        return clone;
    }

    @Override
    public ISSTNode visit(IMethodDeclaration stmt, Void context) {
        MethodDeclaration clone = new MethodDeclaration();
        clone.setBody(visitBlock(stmt.getBody()));
        clone.setEntryPoint(stmt.isEntryPoint());
        clone.setName(stmt.getName());
        return clone;
    }

    @Override
    public ISSTNode visit(IPropertyDeclaration stmt, Void context) {
        PropertyDeclaration clone = new PropertyDeclaration();
        clone.setGet(visitBlock(stmt.getGet()));
        clone.setSet(visitBlock(stmt.getSet()));
        clone.setName(stmt.getName());
        return clone;
    }

    @Override
    public ISSTNode visit(IVariableDeclaration stmt, Void context) {
        VariableDeclaration clone = new VariableDeclaration();
        clone.setReference((IVariableReference) stmt.getReference().accept(this, context));
        clone.setType(stmt.getType());
        return clone;
    }

    @Override
    public ISSTNode visit(IAssignment stmt, Void context) {
        Assignment clone = new Assignment();
        clone.setExpression((IAssignableExpression) stmt.getExpression().accept(this, context));
        clone.setReference((IAssignableReference) stmt.getReference().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(IBreakStatement stmt, Void context) {
        BreakStatement clone = new BreakStatement();
        return clone;
    }

    @Override
    public ISSTNode visit(IContinueStatement stmt, Void context) {
        ContinueStatement clone = new ContinueStatement();
        return clone;
    }

    @Override
    public ISSTNode visit(IExpressionStatement stmt, Void context) {
        ExpressionStatement clone = new ExpressionStatement();
        clone.setExpression((IAssignableExpression) stmt.getExpression().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(IGotoStatement stmt, Void context) {
        GotoStatement clone = new GotoStatement();
        clone.setLabel(stmt.getLabel());
        return clone;
    }

    @Override
    public ISSTNode visit(ILabelledStatement stmt, Void context) {
        LabelledStatement clone = new LabelledStatement();
        clone.setLabel(stmt.getLabel());
        clone.setStatement((IStatement) stmt.getStatement().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(IReturnStatement stmt, Void context) {
        ReturnStatement clone = new ReturnStatement();
        clone.setExpression((ISimpleExpression) stmt.getExpression().accept(this, context));
        clone.setIsVoid(stmt.isVoid());
        return clone;
    }

    @Override
    public ISSTNode visit(IThrowStatement stmt, Void context) {
        ThrowStatement clone = new ThrowStatement();
        clone.setReference((IVariableReference) stmt.getReference().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(IDoLoop block, Void context) {
        DoLoop clone = new DoLoop();
        clone.setBody(visitBlock(block.getBody()));
        clone.setCondition((ILoopHeaderExpression) block.getCondition().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(IForEachLoop block, Void context) {
        ForEachLoop clone = new ForEachLoop();
        clone.setBody(visitBlock(block.getBody()));
        clone.setDeclaration((IVariableDeclaration) block.getDeclaration().accept(this, context));
        clone.setLoopedReference((IVariableReference) block.getLoopedReference().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(IForLoop block, Void context) {
        ForLoop clone = new ForLoop();
        clone.setBody(visitBlock(block.getBody()));
        clone.setCondition((ILoopHeaderExpression) block.getCondition().accept(this, context));
        clone.setInit(visitBlock(block.getInit()));
        clone.setStep(visitBlock(block.getStep()));
        return clone;
    }

    @Override
    public ISSTNode visit(IIfElseBlock block, Void context) {
        IfElseBlock clone = new IfElseBlock();
        clone.setCondition((ISimpleExpression) block.getCondition().accept(this, context));
        clone.setElse(visitBlock(block.getElse()));
        clone.setThen(visitBlock(block.getThen()));
        return clone;
    }

    @Override
    public ISSTNode visit(ILockBlock stmt, Void context) {
        LockBlock clone = new LockBlock();
        clone.setBody(visitBlock(stmt.getBody()));
        clone.setReference((IVariableReference) stmt.getReference().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(ISwitchBlock block, Void context) {
        SwitchBlock clone = new SwitchBlock();
        clone.setDefaultSection(visitBlock(block.getDefaultSection()));
        clone.setReference((IVariableReference) block.getReference().accept(this, context));
        for (ICaseBlock c : block.getSections()) {
            CaseBlock cc = new CaseBlock();
            cc.setBody(visitBlock(c.getBody()));
            cc.setLabel((ISimpleExpression) c.getLabel().accept(this, context));
            clone.getSections().add(cc);
        }
        return clone;
    }

    @Override
    public ISSTNode visit(ITryBlock block, Void context) {
        TryBlock clone = new TryBlock();
        clone.setBody(visitBlock(block.getBody()));
        clone.setFinally(visitBlock(block.getFinally()));
        for (ICatchBlock catchBlock : block.getCatchBlocks()) {
            CatchBlock cloneBlock = new CatchBlock();
            cloneBlock.setBody(visitBlock(catchBlock.getBody()));
            cloneBlock.setKind(catchBlock.getKind());
            cloneBlock.setParameter(catchBlock.getParameter());
            clone.getCatchBlocks().add(cloneBlock);
        }
        return clone;
    }

    @Override
    public ISSTNode visit(IUncheckedBlock block, Void context) {
        UncheckedBlock clone = new UncheckedBlock();
        clone.setBody(visitBlock(block.getBody()));
        return clone;
    }

    @Override
    public ISSTNode visit(IUnsafeBlock block, Void context) {
        UnsafeBlock clone = new UnsafeBlock();
        return clone;
    }

    @Override
    public ISSTNode visit(IUsingBlock block, Void context) {
        UsingBlock clone = new UsingBlock();
        clone.setBody(visitBlock(block.getBody()));
        clone.setReference((IVariableReference) block.getReference().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(IWhileLoop block, Void context) {
        WhileLoop clone = new WhileLoop();
        clone.setBody(visitBlock(block.getBody()));
        clone.setCondition((ILoopHeaderExpression) block.getCondition().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(ICompletionExpression entity, Void context) {
        CompletionExpression clone = new CompletionExpression();
        if (entity.getVariableReference() != null) {
            clone.setObjectReference((IVariableReference) entity.getVariableReference().accept(this, context));
        }
        clone.setTypeReference(entity.getTypeReference());
        clone.setToken(entity.getToken());
        return clone;
    }

    @Override
    public ISSTNode visit(IComposedExpression expr, Void context) {
        ComposedExpression clone = new ComposedExpression();
        for (IVariableReference ref : expr.getReferences()) {
            clone.getReferences().add((IVariableReference) ref.accept(this, context));
        }
        return clone;
    }

    @Override
    public ISSTNode visit(IIfElseExpression expr, Void context) {
        IfElseExpression clone = new IfElseExpression();
        clone.setCondition((ISimpleExpression) expr.getCondition().accept(this, context));
        clone.setElseExpression((ISimpleExpression) expr.getElseExpression().accept(this, context));
        clone.setThenExpression((ISimpleExpression) expr.getThenExpression().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(ILambdaExpression expr, Void context) {
        LambdaExpression clone = new LambdaExpression();
        clone.setBody(visitBlock(expr.getBody()));
        clone.setName(expr.getName());
        return clone;
    }

    @Override
    public ISSTNode visit(ILoopHeaderBlockExpression expr, Void context) {
        LoopHeaderBlockExpression clone = new LoopHeaderBlockExpression();
        clone.setBody(visitBlock(expr.getBody()));
        return clone;
    }

    @Override
    public ISSTNode visit(IConstantValueExpression expr, Void context) {
        ConstantValueExpression clone = new ConstantValueExpression();
        clone.setValue(expr.getValue());
        return clone;
    }

    @Override
    public ISSTNode visit(INullExpression expr, Void context) {
        return new NullExpression();
    }

    @Override
    public ISSTNode visit(IReferenceExpression expr, Void context) {
        ReferenceExpression clone = new ReferenceExpression();
        clone.setReference((IReference) expr.getReference().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(IEventReference eventRef, Void context) {
        EventReference clone = new EventReference();
        clone.setEventName(eventRef.getEventName());
        clone.setReference((IVariableReference) eventRef.getReference().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(IFieldReference fieldRef, Void context) {
        FieldReference clone = new FieldReference();
        clone.setFieldName(fieldRef.getFieldName());
        clone.setReference((IVariableReference) fieldRef.getReference().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(IMethodReference methodRef, Void context) {
        MethodReference clone = new MethodReference();
        clone.setMethodName(methodRef.getMethodName());
        clone.setReference((IVariableReference) methodRef.getReference().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(IPropertyReference methodRef, Void context) {
        PropertyReference clone = new PropertyReference();
        clone.setPropertyName(methodRef.getPropertyName());
        clone.setReference((IVariableReference) methodRef.getReference().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(IVariableReference varRef, Void context) {
        VariableReference clone = new VariableReference();
        clone.setIdentifier(varRef.getIdentifier());
        return clone;
    }

    @Override
    public ISSTNode visit(IUnknownReference unknownRef, Void context) {
        UnknownReference clone = new UnknownReference();
        return clone;
    }

    @Override
    public ISSTNode visit(IUnknownExpression unknownExpr, Void context) {
        UnknownExpression clone = new UnknownExpression();
        return clone;
    }

    @Override
    public ISSTNode visit(IUnknownStatement unknownStmt, Void context) {
        UnknownStatement clone = new UnknownStatement();
        return clone;
    }

    @Override
    public ISSTNode visit(IEventSubscriptionStatement stmt, Void context) {
        EventSubscriptionStatement clone = new EventSubscriptionStatement();
        clone.setExpression((IAssignableExpression) stmt.getExpression().accept(this, context));
        clone.setOperation(stmt.getOperation());
        clone.setReference((IAssignableReference) stmt.getReference().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(ICastExpression expr, Void context) {
        CastExpression clone = new CastExpression();
        clone.setOperator(expr.getOperator());
        clone.setReference((IVariableReference) expr.getReference().accept(this, context));
        clone.setTargetType(expr.getTargetType());
        return clone;
    }

    @Override
    public ISSTNode visit(IIndexAccessExpression expr, Void context) {
        IndexAccessExpression clone = new IndexAccessExpression();
        clone.setReference((IVariableReference) expr.getReference().accept(this, context));
        for (ISimpleExpression e : expr.getIndices()) {
            clone.getIndices().add((ISimpleExpression) e.accept(this, context));
        }
        return clone;
    }

    @Override
    public ISSTNode visit(ITypeCheckExpression expr, Void context) {
        TypeCheckExpression clone = new TypeCheckExpression();
        clone.setReference((IVariableReference) expr.getReference().accept(this, context));
        clone.setType(expr.getType());
        return clone;
    }

    @Override
    public ISSTNode visit(IIndexAccessReference indexAccessRef, Void context) {
        IndexAccessReference clone = new IndexAccessReference();
        clone.setExpression((IIndexAccessExpression) indexAccessRef.getExpression().accept(this, context));
        return clone;
    }

    @Override
    public ISSTNode visit(IBinaryExpression expr, Void context) {
        BinaryExpression clone = new BinaryExpression();
        clone.setLeftOperand((ISimpleExpression) expr.getLeftOperand().accept(this, context));
        clone.setRightOperand((ISimpleExpression) expr.getRightOperand().accept(this, context));
        clone.setOperator(expr.getOperator());
        return clone;
    }

    @Override
    public ISSTNode visit(IUnaryExpression expr, Void context) {
        UnaryExpression clone = new UnaryExpression();
        clone.setOperator(expr.getOperator());
        clone.setOperand((ISimpleExpression) expr.getOperand().accept(this, context));
        return clone;
    }

    private List<IStatement> visitBlock(List<IStatement> body) {
        List<IStatement> clone = new ArrayList<>();
        for (IStatement statement : body) {
            clone.add((IStatement) statement.accept(this, null));
        }
        return clone;
    }
}
