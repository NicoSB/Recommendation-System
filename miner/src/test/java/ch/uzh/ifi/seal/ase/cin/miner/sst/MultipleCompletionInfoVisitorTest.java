package ch.uzh.ifi.seal.ase.cin.miner.sst;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.visitor.ISSTNodeVisitor;
import org.junit.Test;

import java.util.List;

import static cc.kave.commons.utils.ssts.SSTUtils.INT;
import static cc.kave.commons.utils.ssts.SSTUtils.varDecl;
import static ch.uzh.ifi.seal.ase.cin.miner.SSTTestUtils.createSSTWithMethod;
import static ch.uzh.ifi.seal.ase.cin.miner.SSTTestUtils.selectionCompletionStatement;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MultipleCompletionInfoVisitorTest {

    private MultipleCompletionInfoVisitor sut = new MultipleCompletionInfoVisitor();

    @Test
    public void extractsCompletionInfoForMultipleMethodInvocations() {
        IMethodName methodName = mock(IMethodName.class);
        when(methodName.getFullName()).thenReturn("testMethod");
        when(methodName.getDeclaringType()).thenReturn(INT);

        IStatement declaration = varDecl("i", INT);
        IStatement invocation = selectionCompletionStatement("i", "i", "test");
        ISST sst = createSSTWithMethod(methodName, declaration, invocation, invocation);

        walkSST(sst, sut);

        List<SelectionCompletionInfo> completionInfos = sut.getCompletionInfos();

        assertEquals(2, completionInfos.size());
    }

    private void walkSST(ISST sst, ISSTNodeVisitor visitor) {
        sst.accept(visitor, null);
    }

}