package ch.uzh.ifi.seal.ase.cin.recommender.util;

import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.blocks.ICaseBlock;
import cc.kave.commons.model.ssts.expressions.IAssignableExpression;
import cc.kave.commons.model.ssts.expressions.ILoopHeaderExpression;
import cc.kave.commons.model.ssts.expressions.ISimpleExpression;
import cc.kave.commons.model.ssts.expressions.loopheader.ILoopHeaderBlockExpression;
import cc.kave.commons.model.ssts.references.IAssignableReference;
import ch.uzh.ifi.seal.ase.cin.recommender.model.EnclosingNodeKind;
import org.junit.Test;

import static cc.kave.commons.model.ssts.impl.SSTUtil.*;
import static cc.kave.commons.utils.ssts.SSTUtils.completionStmt;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class StatementToNodeKindMapperTest {

    @Test
    public void whenEnclosingNodeIsArgument_returnsArgumentExpression() {
        //TODO
    }

    @Test
    public void whenEnclosingNodeIsWhileLoop_returnsWhile() {
        ILoopHeaderExpression condition = mock(ILoopHeaderExpression.class);
        IStatement completionExpression = completionStmt("i", "token");
        IStatement loop = whileLoop(condition, completionExpression);

        EnclosingNodeKind actual = StatementToNodeKindMapper.map(loop);

        assertEquals(EnclosingNodeKind.WHILE, actual);
    }

    @Test
    public void whenEnclosingNodeIsDoLoop_returnsWhile() {
        ILoopHeaderBlockExpression condition = mock(ILoopHeaderBlockExpression.class);
        IStatement completionExpression = completionStmt("i", "token");
        IStatement loop = doLoop(condition, completionExpression);

        EnclosingNodeKind actual = StatementToNodeKindMapper.map(loop);

        assertEquals(EnclosingNodeKind.WHILE, actual);
    }

    @Test
    public void whenEnclosingNodeIsForLoop_returnsFor() {
        ILoopHeaderBlockExpression condition = mock(ILoopHeaderBlockExpression.class);
        IStatement completionExpression = completionStmt("i", "token");
        IStatement loop = forLoop("i", condition, completionExpression);

        EnclosingNodeKind actual = StatementToNodeKindMapper.map(loop);

        assertEquals(EnclosingNodeKind.FOR, actual);
    }

    @Test
    public void whenEnclosingNodeIsForEachLoop_returnsFor() {
        ILoopHeaderBlockExpression condition = mock(ILoopHeaderBlockExpression.class);
        IStatement completionExpression = completionStmt("i", "token");
        IStatement loop = forEachLoop("i", "i", completionExpression);

        EnclosingNodeKind actual = StatementToNodeKindMapper.map(loop);

        assertEquals(EnclosingNodeKind.FOR, actual);
    }

    @Test
    public void whenEnclosingNodeIsIfElseBlock_returnsBranchingCondition() {
        ISimpleExpression condition = mock(ISimpleExpression.class);
        IStatement ifElseBlock = ifElseBlock(condition, null, null);

        EnclosingNodeKind actual = StatementToNodeKindMapper.map(ifElseBlock);

        assertEquals(EnclosingNodeKind.BRANCHING_CONDITION, actual);
    }

    @Test
    public void whenEnclosingNodeIsSimpleIf_returnsBranchingCondition() {
        ISimpleExpression condition = mock(ISimpleExpression.class);
        IStatement completionExpression = completionStmt("i", "token");
        IStatement ifElseBlock = simpleIf(null, condition, completionExpression);

        EnclosingNodeKind actual = StatementToNodeKindMapper.map(ifElseBlock);

        assertEquals(EnclosingNodeKind.BRANCHING_CONDITION, actual);
    }

    @Test
    public void whenEnclosingNodeIsSwitchBlock_returnsBranchingCondition() {
        ICaseBlock caseBlock = mock(ICaseBlock.class);
        IStatement switchBlock = switchBlock("i", caseBlock);

        EnclosingNodeKind actual = StatementToNodeKindMapper.map(switchBlock);

        assertEquals(EnclosingNodeKind.BRANCHING_CONDITION, actual);
    }

    @Test
    public void whenEnclosingNodeIsAssignment_returnsAssignmentRValue() {
        IAssignableReference reference = mock(IAssignableReference.class);
        IAssignableExpression expression = mock(IAssignableExpression.class);
        IStatement assignment = assign(reference, expression);

        EnclosingNodeKind actual = StatementToNodeKindMapper.map(assignment);

        assertEquals(EnclosingNodeKind.ASSIGNMENT_RVALUE, actual);
    }

    @Test
    public void whenEnclosingNodeIsReturn_returnsReturnValue() {
        ISimpleExpression expression = mock(ISimpleExpression.class);
        IStatement assignment = returnStatement(expression);

        EnclosingNodeKind actual = StatementToNodeKindMapper.map(assignment);

        assertEquals(EnclosingNodeKind.RETURN_VALUE, actual);
    }

}