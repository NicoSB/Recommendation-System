package ch.uzh.ifi.seal.ase.cin.recommender.util;

import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.impl.SST;
import cc.kave.commons.model.ssts.statements.IVariableDeclaration;
import org.junit.Before;
import org.junit.Test;

import static cc.kave.commons.assertions.Asserts.assertTrue;
import static cc.kave.commons.utils.ssts.SSTUtils.*;
import static ch.uzh.ifi.seal.ase.cin.SSTTestUtils.createSSTWithMethod;
import static ch.uzh.ifi.seal.ase.cin.TestUtils.walkSST;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FindFirstVisitorTest {

    private FindFirstVisitor sut;

    @Before
    public void setUp() {
        sut = new FindFirstVisitor(IVariableDeclaration.class);
    }

    @Test
    public void whenSSTDoesNotContainInstanceOfClass_returnsNull() {
        IStatement completion = completionStmt("i", "Token");
        SST sst = createSSTWithMethod(null, completion);

        walkSST(sst, sut);

        assertNull(sut.getResult());
    }

    @Test
    public void whenSSTContainsInstanceOfClass_returnsFirstMatch() {
        IStatement declaration = varDecl("i", INT);
        IStatement declaration2 = varDecl("i2", INT);
        SST sst = createSSTWithMethod(null, declaration, declaration2);

        walkSST(sst, sut);

        assertNotNull(sut.getResult());
        assertTrue(declaration.equals(sut.getResult()));
    }


    @Test
    public void whenVisitorIsReset_resultIsNull() {

        IStatement declaration = varDecl("i", INT);
        SST sst = createSSTWithMethod(null, declaration);

        walkSST(sst, sut);
        sut.reset();

        assertNull(sut.getResult());
    }
}