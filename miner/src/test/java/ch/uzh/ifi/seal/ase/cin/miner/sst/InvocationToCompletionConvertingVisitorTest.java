package ch.uzh.ifi.seal.ase.cin.miner.sst;

import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.expressions.assignable.ICompletionExpression;
import cc.kave.commons.model.ssts.expressions.assignable.IInvocationExpression;
import org.junit.Before;
import org.junit.Test;

import static cc.kave.commons.utils.ssts.SSTUtils.*;
import static ch.uzh.ifi.seal.ase.cin.miner.SSTAsserts.assertDoesNotContain;
import static ch.uzh.ifi.seal.ase.cin.miner.SSTAsserts.assertNodeCount;
import static ch.uzh.ifi.seal.ase.cin.miner.SSTTestUtils.createSSTWithMethod;
import static ch.uzh.ifi.seal.ase.cin.miner.SSTTestUtils.walkSST;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvocationToCompletionConvertingVisitorTest {

    private InvocationToCompletionConvertingVisitor sut;

    @Before
    public void setUp() {
        sut = new InvocationToCompletionConvertingVisitor();
    }

    @Test
    public void convertsMethodInvocation() {
        MethodName methodName = mock(MethodName.class);
        when(methodName.getFullName()).thenReturn("testMethod");
        when(methodName.getDeclaringType()).thenReturn(INT);

        IStatement declaration = varDecl("i", INT);
        IStatement invocation = invStmt("i", methodName);
        ISST sst = createSSTWithMethod(methodName, declaration, invocation, invocation);

        ISST convertedSST = walkSST(sst, sut);

        assertDoesNotContain(convertedSST, IInvocationExpression.class);
        assertNodeCount(convertedSST, 2, ICompletionExpression.class);
    }
}