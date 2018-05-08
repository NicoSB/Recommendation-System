package ch.uzh.ifi.seal.ase.cin.miner;

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

import java.util.HashMap;
import java.util.Map;

import static ch.uzh.ifi.seal.ase.cin.miner.SSTTestUtils.walkSST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SSTAsserts {

    public static void assertDoesNotContain(ISST sst, Class clazz) {
        NodeCountingVisitor visitor = new NodeCountingVisitor(clazz);
        walkSST(sst, visitor);

        assertFalse(visitor.map.getAssignableCount(clazz) > 0);
    }

    public static void assertNodeCount(ISST sst, int expectedCount, Class clazz) {
        NodeCountingVisitor visitor = new NodeCountingVisitor(clazz);
        walkSST(sst, visitor);

        assertEquals(expectedCount, visitor.map.getAssignableCount(clazz));
    }

    private static class NodeCountingVisitor extends AbstractTraversingNodeVisitor {
        private Class<ISSTNode> classToFind;
        private CountingMap map = new CountingMap();

        public NodeCountingVisitor(Class<ISSTNode> classToFind) {
            super();
            this.classToFind = classToFind;
        }

        @Override
        public Object visit(ISST sst, Object o) {
            return super.visit(sst, o);
        }

        @Override
        public Object visit(IDelegateDeclaration stmt, Object o) {
            insertIntoMap(stmt);
            return super.visit(stmt, o);
        }

        @Override
        public Object visit(IEventDeclaration stmt, Object o) {
            insertIntoMap(stmt);
            return super.visit(stmt, o);
        }

        @Override
        public Object visit(IFieldDeclaration stmt, Object o) {
            insertIntoMap(stmt);
            return super.visit(stmt, o);
        }

        @Override
        public Object visit(IMethodDeclaration decl, Object o) {
            insertIntoMap(decl);
            return super.visit(decl, o);
        }

        @Override
        public Object visit(IPropertyDeclaration decl, Object o) {
            insertIntoMap(decl);
            return super.visit(decl, o);
        }

        @Override
        public Object visit(IVariableDeclaration stmt, Object o) {
            insertIntoMap(stmt);
            return super.visit(stmt, o);
        }

        @Override
        public Object visit(IAssignment stmt, Object o) {
            insertIntoMap(stmt);
            return super.visit(stmt, o);
        }

        @Override
        public Object visit(IBreakStatement stmt, Object o) {
            insertIntoMap(stmt);
            return super.visit(stmt, o);
        }

        @Override
        public Object visit(IContinueStatement stmt, Object o) {
            insertIntoMap(stmt);
            return super.visit(stmt, o);
        }

        @Override
        public Object visit(IEventSubscriptionStatement stmt, Object o) {
            insertIntoMap(stmt);
            return super.visit(stmt, o);
        }

        @Override
        public Object visit(IExpressionStatement stmt, Object o) {
            insertIntoMap(stmt);
            return super.visit(stmt, o);
        }

        @Override
        public Object visit(IGotoStatement stmt, Object o) {
            insertIntoMap(stmt);
            return super.visit(stmt, o);
        }

        @Override
        public Object visit(ILabelledStatement stmt, Object o) {
            insertIntoMap(stmt);
            return super.visit(stmt, o);
        }

        @Override
        public Object visit(IReturnStatement stmt, Object o) {
            insertIntoMap(stmt);
            return super.visit(stmt, o);
        }

        @Override
        public Object visit(IThrowStatement stmt, Object o) {
            insertIntoMap(stmt);
            return super.visit(stmt, o);
        }

        @Override
        public Object visit(IDoLoop block, Object o) {
            insertIntoMap(block);
            return super.visit(block, o);
        }

        @Override
        public Object visit(IForEachLoop block, Object o) {
            insertIntoMap(block);
            return super.visit(block, o);
        }

        @Override
        public Object visit(IForLoop block, Object o) {
            insertIntoMap(block);
            return super.visit(block, o);
        }

        @Override
        public Object visit(IIfElseBlock block, Object o) {
            insertIntoMap(block);
            return super.visit(block, o);
        }

        @Override
        public Object visit(ILockBlock stmt, Object o) {
            insertIntoMap(stmt);
            return super.visit(stmt, o);
        }

        @Override
        public Object visit(ISwitchBlock block, Object o) {
            insertIntoMap(block);
            return super.visit(block, o);
        }

        @Override
        public Object visit(ITryBlock block, Object o) {
            insertIntoMap(block);
            return super.visit(block, o);
        }

        @Override
        public Object visit(IUncheckedBlock block, Object o) {
            insertIntoMap(block);
            return super.visit(block, o);
        }

        @Override
        public Object visit(IUnsafeBlock block, Object o) {
            insertIntoMap(block);
            return super.visit(block, o);
        }

        @Override
        public Object visit(IUsingBlock block, Object o) {
            insertIntoMap(block);
            return super.visit(block, o);
        }

        @Override
        public Object visit(IWhileLoop block, Object o) {
            insertIntoMap(block);
            return super.visit(block, o);
        }

        @Override
        public Object visit(ICompletionExpression entity, Object o) {
            insertIntoMap(entity);
            return super.visit(entity, o);
        }

        @Override
        public Object visit(IComposedExpression expr, Object o) {
            insertIntoMap(expr);
            return super.visit(expr, o);
        }

        @Override
        public Object visit(IIfElseExpression expr, Object o) {
            insertIntoMap(expr);
            return super.visit(expr, o);
        }

        @Override
        public Object visit(IInvocationExpression expr, Object o) {
            insertIntoMap(expr);
            return super.visit(expr, o);
        }

        @Override
        public Object visit(ILambdaExpression expr, Object o) {
            insertIntoMap(expr);
            return super.visit(expr, o);
        }

        @Override
        public Object visit(ILoopHeaderBlockExpression expr, Object o) {
            insertIntoMap(expr);
            return super.visit(expr, o);
        }

        @Override
        public Object visit(IConstantValueExpression expr, Object o) {
            insertIntoMap(expr);
            return super.visit(expr, o);
        }

        @Override
        public Object visit(INullExpression expr, Object o) {
            insertIntoMap(expr);
            return super.visit(expr, o);
        }

        @Override
        public Object visit(IReferenceExpression expr, Object o) {
            insertIntoMap(expr);
            return super.visit(expr, o);
        }

        @Override
        public Object visit(ICastExpression expr, Object o) {
            insertIntoMap(expr);
            return super.visit(expr, o);
        }

        @Override
        public Object visit(IIndexAccessExpression expr, Object o) {
            insertIntoMap(expr);
            return super.visit(expr, o);
        }

        @Override
        public Object visit(ITypeCheckExpression expr, Object o) {
            insertIntoMap(expr);
            return super.visit(expr, o);
        }

        @Override
        public Object visit(IBinaryExpression expr, Object o) {
            insertIntoMap(expr);
            return super.visit(expr, o);
        }

        @Override
        public Object visit(IUnaryExpression expr, Object o) {
            insertIntoMap(expr);
            return super.visit(expr, o);
        }

        @Override
        public Object visit(IEventReference ref, Object o) {
            insertIntoMap(ref);
            return super.visit(ref, o);
        }

        @Override
        public Object visit(IFieldReference ref, Object o) {
            insertIntoMap(ref);
            return super.visit(ref, o);
        }

        @Override
        public Object visit(IMethodReference ref, Object o) {
            insertIntoMap(ref);
            return super.visit(ref, o);
        }

        @Override
        public Object visit(IPropertyReference ref, Object o) {
            insertIntoMap(ref);
            return super.visit(ref, o);
        }

        @Override
        public Object visit(IVariableReference ref, Object o) {
            insertIntoMap(ref);
            return super.visit(ref, o);
        }

        @Override
        public Object visit(IIndexAccessReference ref, Object o) {
            insertIntoMap(ref);
            return super.visit(ref, o);
        }

        @Override
        public Object visit(IUnknownReference ref, Object o) {
            insertIntoMap(ref);
            return super.visit(ref, o);
        }

        @Override
        public Object visit(IUnknownExpression unknownExpr, Object o) {
            insertIntoMap(unknownExpr);
            return super.visit(unknownExpr, o);
        }

        @Override
        public Object visit(IUnknownStatement unknownStmt, Object o) {
            insertIntoMap(unknownStmt);
            return super.visit(unknownStmt, o);
        }

        private void insertIntoMap(ISSTNode statement) {
            map.put(statement.getClass());
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }
    }


    private static class CountingMap {
        private HashMap<Class, Integer> map = new HashMap<>();

        public void put(Class clazz) {
            int value = 1;
            if (map.containsKey(clazz)) {
                value = map.get(clazz) + 1;
            }

            map.put(clazz, value);
        }

        public int get(Class clazz) {
            return map.get(clazz);
        }

        public int getAssignableCount(Class clazz) {
            return map.entrySet().stream()
                    .filter(c -> filterAssignables(c, clazz))
                    .mapToInt(Map.Entry::getValue)
                    .sum();
        }

        private boolean filterAssignables(Map.Entry<Class, Integer> entry, Class clazz) {
            Class entryClass = entry.getKey();
            return clazz.isAssignableFrom(entryClass);
        }
    }
}
