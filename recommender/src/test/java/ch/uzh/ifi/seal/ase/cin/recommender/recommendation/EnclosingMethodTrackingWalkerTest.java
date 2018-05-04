package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.statements.IVariableDeclaration;
import cc.kave.commons.model.ssts.visitor.ISSTNodeVisitor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static cc.kave.commons.utils.ssts.SSTUtils.*;
import static ch.uzh.ifi.seal.ase.cin.SSTTestUtils.createSSTWithMethod;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EnclosingMethodTrackingWalkerTest {

    @Mock
    ISSTNodeVisitor visitor;

    @Test
    public void acceptsRegisteredVisitors() {
        EnclosingMethodTrackingWalker sut = new EnclosingMethodTrackingWalker();
        sut.registerVisitor(visitor);

        IVariableDeclaration declaration = varDecl("i", INT);

        ISST sst = createSSTWithMethod(null, declaration);
        sut.walk(sst);

        verify(visitor, times(1)).visit(any(IVariableDeclaration.class), any());
    }

}