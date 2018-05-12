package ch.uzh.ifi.seal.ase.cin.recommender.util;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.commons.model.naming.impl.v0.types.TypeName;
import cc.kave.commons.model.naming.types.ITypeName;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.impl.expressions.assignable.CompletionExpression;
import cc.kave.commons.model.ssts.visitor.ISSTNode;
import org.junit.Test;

import static cc.kave.commons.assertions.Asserts.assertFalse;
import static cc.kave.commons.utils.ssts.SSTUtils.completionStmt;
import static ch.uzh.ifi.seal.ase.cin.SSTTestUtils.createSSTWithMethod;
import static ch.uzh.ifi.seal.ase.cin.recommender.util.SSTUtils.getFullyQualifiedIdentifier;
import static ch.uzh.ifi.seal.ase.cin.recommender.util.SSTUtils.removeGenerics;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SSTUtilsTest {

    @Test
    public void findFirst_whenSSTContainsNodeOfType_returnsNode() {
        IMethodName methodName = mock(IMethodName.class);
        when(methodName.getFullName()).thenReturn("Test");
        when(methodName.isStatic()).thenReturn(true);

        IStatement statement = completionStmt("Test", "");
        ISST sst = createSSTWithMethod(methodName, statement);

        ISSTNode actual = SSTUtils.findFirst(sst, CompletionExpression.class);

        assertTrue(actual instanceof CompletionExpression);
    }

    @Test
    public void getFullyQualifiedIdentifier_whenIdentifierDoesNotContainGenerics_ReturnsIdentifier() {
        String expected = "identifier";

        String actual = getFullyQualifiedIdentifier(expected);

        assertEquals(expected, actual);
    }

    @Test
    public void getFullyQualifiedIdentifier_whenIdentifierContainsGenerics_ReturnsIdentifier() {
        String expected = "System.Collections.Generic.Dictionary";
        String genericInformation = "`2[[TKey -> p:string],[TValue -> System.Drawing.Bitmap, System.Drawing, 2.0.0.0]]";
        String fullName = expected + genericInformation;

        String actual = getFullyQualifiedIdentifier(fullName);

        assertEquals(expected, actual);
    }

    @Test
    public void removeGenerics_replacesSimpleGenericsInTypeName() {
        String identifier = "System.Collections.Generic.Dictionary`2[[TKey -> p:string],[TValue -> System.Drawing.Bitmap, System.Drawing, 2.0.0.0]],Assembly";
        String expected = "System.Collections.Generic.Dictionary`2[[TKey],[TValue]],Assembly";
        TypeName typeName = new TypeName(identifier);

        ITypeName actual = removeGenerics(typeName);

        assertEquals(expected, actual.getIdentifier());
    }

    @Test
    public void removeGenerics_replacesNestedGenericsInTypeName() {
        String identifier = "0M:static [i:System.Collections.Generic.IEnumerable`1[[T -> p:string]], mscorlib, 4.0.0.0] [System.Linq.Enumerable, System.Core, 4.0.0.0]" +
                ".Select`2[[TSource -> p:string],[TResult -> System.Drawing.FontFamily, System.Drawing, 4.0.0.0]]" +
                "(this [i:System.Collections.Generic.IEnumerable`1[[TChar -> p:char]], mscorlib, 4.0.0.0] source, [d:[TResult] [System.Func`2[[T],[TResult]], mscorlib, 4.0.0.0].([T] arg)] selector)";
        String expectedId = "0M:static [i:System.Collections.Generic.IEnumerable`1[[T]], mscorlib, 4.0.0.0] [System.Linq.Enumerable, System.Core, 4.0.0.0]" +
                ".Select`2[[TSource],[TResult]]" +
                "(this [i:System.Collections.Generic.IEnumerable`1[[TChar]], mscorlib, 4.0.0.0] source, [d:[TResult] [System.Func`2[[T],[TResult]], mscorlib, 4.0.0.0].([T] arg)] selector)";
        MethodName methodName = new MethodName(identifier);

        IMethodName actual = removeGenerics(methodName);

        assertEquals(expectedId, actual.getIdentifier());
        assertFalse(actual.getParameters().get(0).getValueType().getIdentifier().contains("->"));
    }
}