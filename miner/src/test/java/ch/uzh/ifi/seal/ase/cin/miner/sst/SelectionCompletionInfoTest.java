package ch.uzh.ifi.seal.ase.cin.miner.sst;

import cc.kave.commons.model.naming.types.ITypeName;
import cc.kave.commons.utils.ssts.completioninfo.ICompletionInfo;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class SelectionCompletionInfoTest {

    @Test
    public void whenExpectedTypeIsNull_hasExpectedTypeReturnsFalse() {
        ICompletionInfo info = new SelectionCompletionInfo();

        assertFalse(info.hasExpectedType());
    }

    @Test
    public void whenExpectedTypeIsNotNull_hasExpectedTypeReturnsTrue() {
        SelectionCompletionInfo info = new SelectionCompletionInfo();
        ITypeName typeName = mock(ITypeName.class);

        info.setExpectedType(typeName);

        assertTrue(info.hasExpectedType());
    }

}