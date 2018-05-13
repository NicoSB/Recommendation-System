package ch.uzh.ifi.seal.ase.cin.recommender.util;

import cc.kave.commons.model.events.completionevents.IProposal;
import cc.kave.commons.model.naming.Names;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.codeelements.IParameterName;
import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.commons.model.naming.types.ITypeName;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.impl.SST;
import cc.kave.commons.model.ssts.impl.declarations.MethodDeclaration;
import cc.kave.commons.model.ssts.visitor.ISSTNode;
import cc.kave.commons.pointsto.analysis.utils.GenericNameUtils;
import sun.reflect.generics.factory.GenericsFactory;

import java.util.ArrayList;
import java.util.List;

public class SSTUtils {
    private static final String GENERICS_PATTERN = ".+`\\d\\[\\[.+]](\\+\\w+)?";
    private static final String GENERIC_INSTANTIATION_PATTERN = " -> [\\w:\\.\\,\\s\\?]+";

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

    public static String getFullyQualifiedIdentifier(String identifier) {
        if (identifier == null || !(identifier.matches(GENERICS_PATTERN)))
            return identifier;

        return identifier.substring(0, identifier.indexOf("`"));
    }

    public static ITypeName removeGenerics(ITypeName typeName) {
        if (!typeName.getIdentifier().contains("->"))
            return typeName;

        ITypeName shortened = GenericNameUtils.eraseGenericInstantiations(typeName);

        return removeGenerics(shortened);
    }

    public static IMethodName removeGenerics(IMethodName methodName) {
        if (methodName == null || methodName.getIdentifier() == null)
            return methodName;

        String identifier = methodName.getIdentifier().replaceAll(GENERIC_INSTANTIATION_PATTERN, "");

        return Names.newMethod(identifier);
    }
}
