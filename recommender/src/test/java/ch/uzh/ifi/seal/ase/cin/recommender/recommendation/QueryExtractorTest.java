package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.impl.v0.types.TypeName;
import cc.kave.commons.model.naming.types.ITypeName;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.expressions.assignable.ICompletionExpression;
import cc.kave.commons.model.ssts.impl.SST;
import ch.uzh.ifi.seal.ase.cin.recommender.model.MethodKind;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import org.junit.Test;

import static cc.kave.commons.utils.ssts.SSTUtils.*;
import static ch.uzh.ifi.seal.ase.cin.TestUtils.assertContains;
import static ch.uzh.ifi.seal.ase.cin.SSTTestUtils.createSSTWithMethod;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QueryExtractorTest {

    @Test
    public void whenNoCompletionExpressionWasFound_ReturnsNull () {
        ISST sst = new SST();

        Query actual = QueryExtractor.extractQuery(sst);

        assertNull(actual);
    }

    @Test
    public void extractsTypeName() {
        ITypeName typeName = new TypeName("Test,assembly");
        IStatement statement = completionStmt(typeName, "");
        ISST sst = createSSTWithMethod(null, statement);

        Query actual = QueryExtractor.extractQuery(sst);

        assertEquals(typeName.getFullName(), actual.getType());
    }

    @Test
    public void whenTypeIsExpected_extractsExpectedType() {
        ITypeName typeName = new TypeName("Test,assembly");

        String variable = "i";
        IStatement variableDeclaration = varDecl(variable, typeName);
        ICompletionExpression completionExpression = completionExpr(typeName, "ge");
        IStatement assignment = assign(variable, completionExpression);

        ISST sst = createSSTWithMethod(null, variableDeclaration, assignment);

        Query actual = QueryExtractor.extractQuery(sst);

        assertContains(actual.getExpectedTypes(), typeName.getFullName());
    }

    @Test
    public void whenNoTypeIsExpected_expectedTypeIsEmpty() {
        ITypeName typeName = new TypeName("Test,assembly");
        IStatement statement = completionStmt(typeName, "");
        ISST sst = createSSTWithMethod(null, statement);

        Query actual = QueryExtractor.extractQuery(sst);

        assertContains(actual.getExpectedTypes(), "");
    }

    @Test
    public void whenEnclosingMethodIsStatic_enclosingMethodKindIsClass() {
        IMethodName methodName = mock(IMethodName.class);
        when(methodName.getFullName()).thenReturn("test");
        when(methodName.isStatic()).thenReturn(true);

        ITypeName typeName = new TypeName("Test,assembly");
        IStatement statement = completionStmt(typeName, "");

        ISST sst = createSSTWithMethod(methodName, statement);

        Query actual = QueryExtractor.extractQuery(sst);

        assertEquals(MethodKind.CLASS, actual.getEnclosingMethodKind());
    }

    @Test
    public void whenEnclosingMethodIsNotStatic_enclosingMethodKindIsInstance() {
        IMethodName methodName = mock(IMethodName.class);
        when(methodName.getFullName()).thenReturn("test");
        when(methodName.isStatic()).thenReturn(false);

        ITypeName typeName = new TypeName("Test,assembly");
        IStatement statement = completionStmt(typeName, "");

        ISST sst = createSSTWithMethod(methodName, statement);

        Query actual = QueryExtractor.extractQuery(sst);

        assertEquals(MethodKind.INSTANCE, actual.getEnclosingMethodKind());
    }
}