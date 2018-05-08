package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.expressions.assignable.ICompletionExpression;
import cc.kave.commons.model.ssts.impl.expressions.assignable.CompletionExpression;
import org.junit.Before;
import org.junit.Test;

import static cc.kave.commons.utils.ssts.SSTUtils.completionStmt;
import static ch.uzh.ifi.seal.ase.cin.SSTTestUtils.createSSTWithMethod;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnclosingMethodTrackingVisitorTest {

    private EnclosingMethodTrackingVisitor sut;
    private EnclosingMethodTrackingWalker walker;

    @Before
    public void setUp() {
        sut = new EnclosingMethodTrackingVisitor();

        walker = new EnclosingMethodTrackingWalker();
        walker.registerVisitor(sut);
    }

    @Test
    public void registersEnclosingMethodProperties() {
        IMethodName methodName = mock(IMethodName.class);
        when(methodName.getFullName()).thenReturn("Test");
        when(methodName.isStatic()).thenReturn(true);

        MethodProperties expected = new MethodProperties(methodName);

        IStatement statement = completionStmt("Test", "");
        ISST sst = createSSTWithMethod(methodName, statement);

        walker.walk(sst);
        MethodProperties actual = sut.getEnclosingMethodProperties(statement);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void whenQueryFirstOfClass_returnsFirstMatch() {
        IMethodName methodName = mock(IMethodName.class);
        when(methodName.getFullName()).thenReturn("Test");
        when(methodName.isStatic()).thenReturn(true);

        MethodProperties expected = new MethodProperties(methodName);

        IStatement statement = completionStmt("Test", "");
        ISST sst = createSSTWithMethod(methodName, statement);

        walker.walk(sst);
        MethodProperties actual = sut.getFirstOfClass(CompletionExpression.class);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void whenParentClassIsProvided_returnsFirstSubclassMatch() {
        IMethodName methodName = mock(IMethodName.class);
        when(methodName.getFullName()).thenReturn("Test");
        when(methodName.isStatic()).thenReturn(true);

        MethodProperties expected = new MethodProperties(methodName);

        IStatement statement = completionStmt("Test", "");
        ISST sst = createSSTWithMethod(methodName, statement);

        walker.walk(sst);
        MethodProperties actual = sut.getFirstOfClass(ICompletionExpression.class);

        assertTrue(expected.equals(actual));
    }
}