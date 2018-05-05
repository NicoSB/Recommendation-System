package ch.uzh.ifi.seal.ase.cin.recommender.util;

import cc.kave.commons.model.ssts.blocks.*;
import cc.kave.commons.model.ssts.statements.IAssignment;
import cc.kave.commons.model.ssts.statements.IReturnStatement;
import cc.kave.commons.model.ssts.visitor.ISSTNode;
import ch.uzh.ifi.seal.ase.cin.recommender.model.EnclosingNodeKind;

public class StatementToNodeKindMapper {

    public static EnclosingNodeKind map(ISSTNode node) {
        if (node instanceof IWhileLoop) {
            return EnclosingNodeKind.WHILE;
        }

        if (node instanceof IDoLoop) {
            return EnclosingNodeKind.WHILE;
        }

        if (node instanceof IForLoop) {
            return EnclosingNodeKind.FOR;
        }

        if (node instanceof IForEachLoop) {
            return EnclosingNodeKind.FOR;
        }

        if (node instanceof IIfElseBlock) {
            return EnclosingNodeKind.BRANCHING_CONDITION;
        }

        if (node instanceof ISwitchBlock) {
            return EnclosingNodeKind.BRANCHING_CONDITION;
        }

        if (node instanceof IAssignment) {
            return EnclosingNodeKind.ASSIGNMENT_RVALUE;
        }

        if (node instanceof IReturnStatement) {
            return EnclosingNodeKind.RETURN_VALUE;
        }

        return EnclosingNodeKind.DEFAULT;
    }
}
