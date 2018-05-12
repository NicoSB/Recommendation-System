package ch.uzh.ifi.seal.ase.cin.recommender.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class QueryTest {

    @Test
    public void removesGenericsInformation() {
        String expected = "System.Collections.Generic.Dictionary";
        String genericInformation = "`2[[TKey -> p:string],[TValue -> System.Drawing.Bitmap, System.Drawing, 2.0.0.0]]";
        String fullName = expected + genericInformation;

        Query query = new Query(fullName);

        assertEquals(expected, query.getType());
    }

    @Test
    public void doesNotModifyNormalTypeNames() {
        String expected = "System.Collections.Generic.Dictionary";

        Query query = new Query(expected);

        assertEquals(expected, query.getType());
    }

    @Test
    public void WhenInstancesAreSame_EqualsIsTrue() {
        Query query = new Query("Type");

        assertEquals(query, query);
    }

    @Test
    public void WhenInstancesAreSame_HashCodesAreSame() {
        Query query = new Query("Type");

        assertEquals(query.hashCode(), query.hashCode());
    }

    @Test
    public void WhenInstancesAreEqual_EqualsIsTrue() {
        Query query1 = new Query("Type");
        Query query2 = new Query("Type");

        assertEquals(query1, query2);
    }

    @Test
    public void WhenInstancesAreEqual_HashCodesAreEqual() {
        Query query1 = new Query("Type");
        Query query2 = new Query("Type");

        assertEquals(query1.hashCode(), query2.hashCode());
    }

    @Test
    public void WhenTypesAreDifferent_EqualsIsFalse() {
        Query query1 = new Query("Type1");
        Query query2 = new Query("Type2");

        assertNotEquals(query1, query2);
    }

    @Test
    public void WhenTypesAreDifferent_HashCodesAreDifferent() {
        Query query1 = new Query("Type1");
        Query query2 = new Query("Type2");

        assertNotEquals(query1.hashCode(), query2.hashCode());
    }

    @Test
    public void WhenExpectedTypesAreDifferent_EqualsIsFalse() {
        String type = "Type";
        String[] expectedTypes1 = new String[]{type};
        String[] expectedTypes2 = new String[]{};

        Query query1 = new Query(type);
        query1.setExpectedTypes(expectedTypes1);

        Query query2 = new Query(type);
        query2.setExpectedTypes(expectedTypes2);

        assertNotEquals(query1, query2);
    }

    @Test
    public void WhenExpectedTypesAreDifferent_HashCodesAreDifferent() {
        String type = "Type";
        String[] expectedTypes1 = new String[]{type};
        String[] expectedTypes2 = new String[]{};

        Query query1 = new Query(type);
        query1.setExpectedTypes(expectedTypes1);

        Query query2 = new Query(type);
        query2.setExpectedTypes(expectedTypes2);

        assertNotEquals(query1.hashCode(), query2.hashCode());
    }

    @Test
    public void WhenEnclosingNodeKindsAreDifferent_EqualsIsFalse() {
        String type = "Type";
        EnclosingNodeKind enclosingNodeKind1 = EnclosingNodeKind.ALLOCATION_EXPRESSION;
        EnclosingNodeKind enclosingNodeKind2 = EnclosingNodeKind.DEFAULT;

        Query query1 = new Query(type);
        query1.setEnclosingNodeKind(enclosingNodeKind1);

        Query query2 = new Query(type);
        query2.setEnclosingNodeKind(enclosingNodeKind2);


        assertNotEquals(query1, query2);
    }

    @Test
    public void WhenEnclosingNodeKindsAreDifferent_HashCodesAreDifferent() {
        String type = "Type";
        EnclosingNodeKind enclosingNodeKind1 = EnclosingNodeKind.ALLOCATION_EXPRESSION;
        EnclosingNodeKind enclosingNodeKind2 = EnclosingNodeKind.DEFAULT;

        Query query1 = new Query(type);
        query1.setEnclosingNodeKind(enclosingNodeKind1);

        Query query2 = new Query(type);
        query2.setEnclosingNodeKind(enclosingNodeKind2);

        assertNotEquals(query1.hashCode(), query2.hashCode());
    }

    @Test
    public void WhenCompletionNodeKindsAreDifferent_EqualsIsFalse() {
        String type = "Type";
        CompletionNodeKind completionNodeKind1 = CompletionNodeKind.CONSTANT;
        CompletionNodeKind completionNodeKind2 = CompletionNodeKind.DEFAULT;

        Query query1 = new Query(type);
        query1.setCompletionNodeKind(completionNodeKind1);

        Query query2 = new Query(type);
        query2.setCompletionNodeKind(completionNodeKind2);

        assertNotEquals(query1, query2);
    }

    @Test
    public void WhenCompletionNodeKindsAreDifferent_HashCodesAreDifferent() {
        String type = "Type";
        CompletionNodeKind completionNodeKind1 = CompletionNodeKind.CONSTANT;
        CompletionNodeKind completionNodeKind2 = CompletionNodeKind.DEFAULT;

        Query query1 = new Query(type);
        query1.setCompletionNodeKind(completionNodeKind1);

        Query query2 = new Query(type);
        query2.setCompletionNodeKind(completionNodeKind2);

        assertNotEquals(query1.hashCode(), query2.hashCode());
    }

    @Test
    public void WhenEnclosingMethodKindsAreDifferent_EqualsIsFalse() {
        String type = "Type";
        MethodKind methodKind1 = MethodKind.INSTANCE;
        MethodKind methodKind2 = MethodKind.CLASS;

        Query query1 = new Query(type);
        query1.setEnclosingMethodKind(methodKind1);

        Query query2 = new Query(type);
        query2.setEnclosingMethodKind(methodKind2);

        assertNotEquals(query1, query2);
    }

    @Test
    public void WhenEnclosingMethodKindsAreDifferent_HashCodesAreDifferent() {
        String type = "Type";
        MethodKind methodKind1 = MethodKind.INSTANCE;
        MethodKind methodKind2 = MethodKind.CLASS;

        Query query1 = new Query(type);
        query1.setEnclosingMethodKind(methodKind1);

        Query query2 = new Query(type);
        query2.setEnclosingMethodKind(methodKind2);

        assertNotEquals(query1.hashCode(), query2.hashCode());
    }

    @Test
    public void WhenEnclosingMethodVisibilitiesAreDifferent_EqualsIsFalse() {
        String type = "Type";
        Visibility visibility1 = Visibility.PRIVATE;
        Visibility visibility2 = Visibility.PUBLIC;

        Query query1 = new Query(type);
        query1.setEnclosingMethodVisibility(visibility1);

        Query query2 = new Query(type);
        query2.setEnclosingMethodVisibility(visibility2);

        assertNotEquals(query1, query2);
    }

    @Test
    public void WhenEnclosingMethodVisibilitiesAreDifferent_HashCodesAreDifferent() {
        String type = "Type";
        Visibility visibility1 = Visibility.PRIVATE;
        Visibility visibility2 = Visibility.PUBLIC;

        Query query1 = new Query(type);
        query1.setEnclosingMethodVisibility(visibility1);

        Query query2 = new Query(type);
        query2.setEnclosingMethodVisibility(visibility2);

        assertNotEquals(query1.hashCode(), query2.hashCode());
    }

    @Test
    public void isUnknown_whenTypeIsQuestionMark_returnsTrue() {
        Query query = new Query("?");

        assertTrue(query.isTypeUnknown());
    }

    @Test
    public void isUnknown_whenTypeIsNull_returnsTrue() {
        Query query = new Query(null);

        assertTrue(query.isTypeUnknown());
    }

    @Test
    public void isUnknown_whenTypeIsNormal_returnsFalse() {
        Query query = new Query("Test");

        assertFalse(query.isTypeUnknown());
    }
}