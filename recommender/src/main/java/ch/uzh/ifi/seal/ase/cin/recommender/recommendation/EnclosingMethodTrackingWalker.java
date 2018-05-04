package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.visitor.ISSTNodeVisitor;
import com.google.common.collect.Lists;

import java.util.List;

public class EnclosingMethodTrackingWalker {
    private List<ISSTNodeVisitor> visitors = Lists.newArrayList();

    public void registerVisitor(ISSTNodeVisitor visitor) {
        visitors.add(visitor);
    }

    public void walk(ISST sst) {
        walkMethods(sst);
    }

    private void walkMethods(ISST sst) {
        sst.getMethods()
                .forEach(this::walkMethod);
    }

    private void walkMethod(IMethodDeclaration method) {
        IMethodName methodName = method.getName();
        MethodProperties properties = new MethodProperties(methodName);
        method.getBody()
                .forEach(stmt -> acceptVisitors(stmt, properties));
    }

    private void acceptVisitors(IStatement statement, MethodProperties properties) {
        visitors.forEach(v -> statement.accept(v, properties));
    }
}
