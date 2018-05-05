package ch.uzh.ifi.seal.ase.cin.recommender.util;

import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.impl.SST;
import cc.kave.commons.model.ssts.impl.declarations.MethodDeclaration;
import cc.kave.commons.model.ssts.visitor.ISSTNode;

public class SSTUtils {

    public static <TExpression extends ISSTNode> TExpression findFirst(ISSTNode node, Class<TExpression> clazz) {
        if (!(ISSTNode.class.isAssignableFrom(clazz)))
            throw new IllegalArgumentException(String.format("'%s' is not a subclass of ISSTNode", clazz));

        if (node instanceof SST) {
            return traverseSST((SST) node, clazz);
        }

        if (node instanceof MethodDeclaration) {
            ISSTNode methodResult = traverseMethodBody((MethodDeclaration) node, clazz);
            if (methodResult != null && clazz.isInstance(methodResult))
                return (TExpression) methodResult;
        }

        if (clazz.isInstance(node))
            return (TExpression) node;

        return null;
    }

    private static <TExpression extends ISSTNode> TExpression traverseMethodBody(MethodDeclaration node, Class<TExpression> clazz) {
        FindFirstVisitor visitor = new FindFirstVisitor(clazz);

        for (IStatement statement : node.getBody()) {
            statement.accept(visitor, null);
        }

        ISSTNode result = visitor.getResult();
        return clazz.isInstance(result) ? (TExpression) result : null;
    }

    private static <TExpression extends ISSTNode> TExpression traverseSST(SST node, Class<TExpression> clazz) {
        for (IMethodDeclaration method : node.getMethods()) {
            ISSTNode methodResult = findFirst(method, clazz);
            if (methodResult != null && clazz.isInstance(methodResult))
                return (TExpression) methodResult;
        }

        return null;
    }
}
