package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.naming.impl.v0.types.TypeName;
import cc.kave.commons.model.naming.types.ITypeName;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.expressions.assignable.ICompletionExpression;
import cc.kave.commons.model.ssts.impl.SST;
import cc.kave.commons.model.ssts.impl.declarations.MethodDeclaration;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Arrays;

import static cc.kave.commons.utils.ssts.SSTUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class QueryExtractorTest {

    @Test
    public void whenNoCompletionExpressionWasFound_ReturnsNull () {
        ISST sst = new SST();

        Query actual = QueryExtractor.extractQuery(sst);

        assertNull(actual);
    }

    @Test
    public void extractsTypeName() {
        ITypeName typeName = new TypeName("assembly,Test");
        IStatement statement = completionStmt(typeName, "");
        ISST sst = buildSST(statement);

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

        ISST sst = buildSST(variableDeclaration, assignment);

        Query actual = QueryExtractor.extractQuery(sst);

        assertContains(actual.getExpectedTypes(), typeName.getFullName());
    }

    @Test
    public void whenNoTypeIsExpected_expectedTypeIsEmpty() {
        ITypeName typeName = new TypeName("Test,assembly");
        IStatement statement = completionStmt(typeName, "");
        ISST sst = buildSST(statement);

        Query actual = QueryExtractor.extractQuery(sst);

        assertContains(actual.getExpectedTypes(), "");
    }

    private static SST buildSST(IStatement... body) {
        MethodDeclaration md = new MethodDeclaration();
        md.setBody(Lists.newArrayList(body));

        SST sst = new SST();
        sst.getMethods().add(md);

        return sst;
    }

    private void assertContains(Object[] objects, Object obj) {
        assertTrue(Arrays.asList(objects).contains(obj));
    }
}