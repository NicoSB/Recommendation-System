package ch.uzh.ifi.seal.ase.cin.miner;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.expressions.assignable.ICompletionExpression;
import cc.kave.commons.model.ssts.impl.SST;
import cc.kave.commons.model.ssts.impl.declarations.MethodDeclaration;
import cc.kave.commons.model.ssts.statements.IExpressionStatement;
import cc.kave.commons.model.ssts.visitor.ISSTNodeVisitor;
import ch.uzh.ifi.seal.ase.cin.miner.sst.SelectionCompletionExpression;
import com.google.common.collect.Lists;

import static cc.kave.commons.utils.ssts.SSTUtils.exprStmt;
import static cc.kave.commons.utils.ssts.SSTUtils.varRef;

public class SSTTestUtils {

    public static SST createSSTWithMethod(IMethodName methodName, IStatement... methodBody) {
        MethodDeclaration md = new MethodDeclaration();
        if (methodName != null)
            md.setName(methodName);
        md.setBody(Lists.newArrayList(methodBody));

        SST sst = new SST();
        sst.getMethods().add(md);

        return sst;
    }

    public static ISST walkSST(ISST sst, ISSTNodeVisitor visitor) {
        return (ISST) sst.accept(visitor, null);
    }


    public static IExpressionStatement selectionCompletionStatement(String id, String token, String selection) {
        return exprStmt(selectionCompletionExpression(id, token, selection));
    }

    private static ICompletionExpression selectionCompletionExpression(String id, String token, String selection) {
        SelectionCompletionExpression expr = new SelectionCompletionExpression(selection);
        expr.setObjectReference(varRef(id));
        expr.setToken(token);
        return expr;
    }
}
