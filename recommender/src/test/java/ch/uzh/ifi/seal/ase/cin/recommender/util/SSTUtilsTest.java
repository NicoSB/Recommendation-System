package ch.uzh.ifi.seal.ase.cin.recommender.util;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.impl.expressions.assignable.CompletionExpression;
import cc.kave.commons.model.ssts.visitor.ISSTNode;
import org.junit.Test;

import static cc.kave.commons.utils.ssts.SSTUtils.completionStmt;
import static ch.uzh.ifi.seal.ase.cin.SSTTestUtils.createSSTWithMethod;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SSTUtilsTest {

    @Test
    public void whenSSTContainsNodeOfType_returnsNode() {
        IMethodName methodName = mock(IMethodName.class);
        when(methodName.getFullName()).thenReturn("Test");
        when(methodName.isStatic()).thenReturn(true);

        IStatement statement = completionStmt("Test", "");
        ISST sst = createSSTWithMethod(methodName, statement);

        ISSTNode actual = SSTUtils.findFirst(sst, CompletionExpression.class);

        assertTrue(actual instanceof CompletionExpression);
    }

}