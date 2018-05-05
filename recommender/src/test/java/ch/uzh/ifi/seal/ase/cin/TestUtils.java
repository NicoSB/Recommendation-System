package ch.uzh.ifi.seal.ase.cin;

import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.impl.SST;
import cc.kave.commons.model.ssts.visitor.ISSTNodeVisitor;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class TestUtils {

    public static void assertContains(Object[] objects, Object obj) {
        assertTrue(Arrays.asList(objects).contains(obj));
    }

    public static void assertContains(Collection collection, Object obj) {
        assertTrue(collection.contains(obj));
    }

    public static void walkSST(SST sst, ISSTNodeVisitor visitor) {
        sst.getMethods().forEach(method -> walkMethod(method, visitor));
    }

    private static void walkMethod(IMethodDeclaration declaration, ISSTNodeVisitor visitor) {
        for (IStatement statement : declaration.getBody()) {
            statement.accept(visitor, null);
        }
    }
}
