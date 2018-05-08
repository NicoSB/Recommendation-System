package ch.uzh.ifi.seal.ase.cin.miner.sst;

import cc.kave.commons.model.naming.Names;
import cc.kave.commons.model.naming.codeelements.IParameterName;
import cc.kave.commons.model.naming.types.ITypeName;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.blocks.*;
import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.declarations.IPropertyDeclaration;
import cc.kave.commons.model.ssts.expressions.assignable.ICompletionExpression;
import cc.kave.commons.model.ssts.expressions.assignable.ILambdaExpression;
import cc.kave.commons.model.ssts.impl.visitor.AbstractTraversingNodeVisitor;
import cc.kave.commons.model.ssts.references.IAssignableReference;
import cc.kave.commons.model.ssts.statements.IAssignment;
import cc.kave.commons.model.ssts.statements.IVariableDeclaration;
import cc.kave.commons.utils.ssts.completioninfo.TypeOfAssignableReferenceVisitor;
import cc.kave.commons.utils.ssts.completioninfo.VariableScope;

import java.util.ArrayList;
import java.util.List;

public class MultipleCompletionInfoVisitor extends AbstractTraversingNodeVisitor<Void, Void> {

    private TypeOfAssignableReferenceVisitor refTypeVisitor = new TypeOfAssignableReferenceVisitor();
    private VariableScope<ITypeName> variables = new VariableScope<>(VariableScope.ErrorHandling.IGNORE);
    private List<SelectionCompletionInfo> completionInfos = new ArrayList<>();
    private SelectionCompletionInfo currentCompletionInfo;

    @Override
    public Void visit(ISST sst, Void aVoid) {
        super.visit(sst, aVoid);
        currentCompletionInfo = new SelectionCompletionInfo();

        return null;
    }

    @Override
    public Void visit(ICompletionExpression expr, Void context) {
        if (!(expr instanceof SelectionCompletionExpression))
            return null;

        completionInfos.add(currentCompletionInfo);
        currentCompletionInfo = new SelectionCompletionInfo();

        currentCompletionInfo.setCompletionExpr(expr);
        SelectionCompletionExpression selectionExpression = (SelectionCompletionExpression) expr;
        currentCompletionInfo.setSelection(selectionExpression.getSelection());

        if (expr.getTypeReference() != null) {
            currentCompletionInfo.setTriggeredType(expr.getTypeReference());
        } else if (expr.getVariableReference() != null) {
            String id = expr.getVariableReference().getIdentifier();
            currentCompletionInfo.setTriggeredType(variables.isDeclared(id) ? variables.get(id) : Names.getUnknownType());
        }

        return null;
    }

    @Override
    public Void visit(IPropertyDeclaration decl, Void context) {
        variables.open();
        visit(decl.getGet(), context);
        variables.close();
        variables.open();
        visit(decl.getSet(), context);
        variables.close();
        return null;
    }

    @Override
    public Void visit(IMethodDeclaration decl, Void context) {
        variables.open();

        for (IParameterName p : decl.getName().getParameters()) {
            variables.declare(p.getName(), p.getValueType());
        }

        super.visit(decl, context);
        variables.close();
        return null;
    }

    @Override
    public Void visit(IAssignment stmt, Void context) {
        if (stmt.getExpression() instanceof ICompletionExpression) {
            IAssignableReference ref = stmt.getReference();
            currentCompletionInfo = new SelectionCompletionInfo();
            currentCompletionInfo.setExpectedType(ref.accept(refTypeVisitor, variables));
        }
        return super.visit(stmt, context);
    }

    @Override
    public Void visit(IVariableDeclaration stmt, Void context) {
        variables.declare(stmt.getReference().getIdentifier(), stmt.getType());
        return super.visit(stmt, context);
    }

    @Override
    public Void visit(IDoLoop loop, Void context) {
        variables.open();
        super.visit(loop, context);
        // TODO right now, the condition is treated within the scope, which is not
        // correct!
        variables.close();
        return null;
    }

    @Override
    public Void visit(IForLoop loop, Void context) {
        variables.open();
        super.visit(loop, context);
        variables.close();
        return null;
    }

    @Override
    public Void visit(IForEachLoop loop, Void context) {
        variables.open();
        super.visit(loop, context);
        variables.close();
        return null;
    }

    @Override
    public Void visit(IIfElseBlock block, Void context) {

        block.getCondition().accept(this, context);
        variables.open();
        visit(block.getThen(), context);
        variables.close();
        variables.open();
        visit(block.getElse(), context);
        variables.close();
        return null;
    }

    @Override
    public Void visit(ILockBlock loop, Void context) {
        variables.open();
        super.visit(loop, context);
        variables.close();
        return null;
    }

    @Override
    public Void visit(ISwitchBlock block, Void context) {
        variables.open();
        super.visit(block, context);
        variables.close();
        return null;
    }

    @Override
    public Void visit(ITryBlock block, Void context) {
        variables.open();
        visit(block.getBody(), context);
        variables.close();
        for (ICatchBlock cb : block.getCatchBlocks()) {
            variables.open();

            IParameterName p = cb.getParameter();
            variables.declare(p.getName(), p.getValueType());

            visit(cb.getBody(), context);
            variables.close();
        }
        variables.open();
        visit(block.getFinally(), context);
        variables.close();
        return null;
    }

    @Override
    public Void visit(IUncheckedBlock block, Void context) {
        variables.open();
        super.visit(block, context);
        variables.close();
        return null;
    }

    @Override
    public Void visit(IUsingBlock block, Void context) {
        variables.open();
        super.visit(block, context);
        variables.close();
        return null;
    }

    @Override
    public Void visit(IWhileLoop loop, Void context) {
        variables.open();
        super.visit(loop, context);
        variables.close();
        return null;
    }

    @Override
    public Void visit(ILambdaExpression expr, Void context) {
        variables.open();
        for (IParameterName p : expr.getName().getParameters()) {
            variables.declare(p.getName(), p.getValueType());
        }
        super.visit(expr, context);
        variables.close();
        return null;
    }

    public List<SelectionCompletionInfo> getCompletionInfos() {
        return completionInfos;
    }
}
