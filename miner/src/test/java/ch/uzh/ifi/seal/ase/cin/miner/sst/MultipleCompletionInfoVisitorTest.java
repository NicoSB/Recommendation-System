package ch.uzh.ifi.seal.ase.cin.miner.sst;

import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
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
        MethodName methodName1 = new MethodName("[p:void] [System.Int32, SharpCompress].RemoveEntry([TEntry] entry)");
        MethodName methodName2 = new MethodName("[p:void] [System.Int32, SharpCompress].RemoveEntry([TEntry] entry)");

        IStatement declaration = varDecl("i", INT);
        IStatement invocation1 = selectionCompletionStatement("i", "i", methodName1);
        IStatement invocation2 = selectionCompletionStatement("i", "i", methodName2);
        ISST sst = createSSTWithMethod(methodName1, declaration, invocation1, invocation2);

        walkSST(sst, sut);

        List<SelectionCompletionInfo> completionInfos = sut.getCompletionInfos();

        assertEquals(2, completionInfos.size());
        assertEquals(methodName1.getIdentifier(), completionInfos.get(0).getSelection().getIdentifier());
        assertEquals(methodName1, completionInfos.get(0).getSelection());
        assertEquals(methodName2, completionInfos.get(1).getSelection());
    }

    private void walkSST(ISST sst, ISSTNodeVisitor visitor) {
        sst.accept(visitor, null);
    }

}