package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import ch.uzh.ifi.seal.ase.cin.recommender.model.MethodKind;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Visibility;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MethodPropertiesTest {

    @Test
    public void whenInstancesAreSame_EqualsIsTrue() {
        IMethodName methodName = createMethodNameMock("Test", false, Visibility.PUBLIC);
        MethodProperties properties = new MethodProperties(methodName);

        assertTrue(properties.equals(properties));
    }

    @Test
    public void whenInstancesAreSame_HashCodesAreEqual() {
        IMethodName methodName = createMethodNameMock("Test", false, Visibility.PUBLIC);
        MethodProperties properties = new MethodProperties(methodName);

        assertEquals(properties.hashCode(), properties.hashCode());
    }

    @Test
    public void whenInstancesAreEqual_EqualsIsTrue() {
        IMethodName methodName = createMethodNameMock("Test", false, Visibility.PUBLIC);
        MethodProperties properties1 = new MethodProperties(methodName);
        MethodProperties properties2 = new MethodProperties(methodName);

        assertTrue(properties1.equals(properties2));
    }

    @Test
    public void whenInstancesAreEqual_HashCodesAreEqual() {
        IMethodName methodName = createMethodNameMock("Test", false, Visibility.PUBLIC);
        MethodProperties properties1 = new MethodProperties(methodName);
        MethodProperties properties2 = new MethodProperties(methodName);

        assertEquals(properties1.hashCode(), properties2.hashCode());
    }

    @Test
    public void whenMethodNamesAreDifferent_EqualsIsFalse() {
        IMethodName methodName1 = createMethodNameMock("Test1", false, Visibility.PUBLIC);
        IMethodName methodName2 = createMethodNameMock("Test2", false, Visibility.PUBLIC);
        MethodProperties properties1 = new MethodProperties(methodName1);
        MethodProperties properties2 = new MethodProperties(methodName2);

        assertFalse(properties1.equals(properties2));
    }

    @Test
    public void whenMethodNamesAreNotEqual_HashCodesAreDifferent() {
        IMethodName methodName1 = createMethodNameMock("Test1", false, Visibility.PUBLIC);
        IMethodName methodName2 = createMethodNameMock("Test2", false, Visibility.PUBLIC);
        MethodProperties properties1 = new MethodProperties(methodName1);
        MethodProperties properties2 = new MethodProperties(methodName2);

        assertNotEquals(properties1.hashCode(), properties2.hashCode());
    }

    @Test
    public void whenMethodIsStatic_IsClassMethod() {
        IMethodName methodName = createMethodNameMock("Test", true, Visibility.PUBLIC);

        MethodProperties properties = new MethodProperties(methodName);

        assertEquals(MethodKind.CLASS, properties.getMethodKind());
    }

    @Test
    public void whenMethodIsNotStatic_IsInstanceMethod() {
        IMethodName methodName = createMethodNameMock("Test", false, Visibility.PUBLIC);

        MethodProperties properties = new MethodProperties(methodName);

        assertEquals(MethodKind.INSTANCE, properties.getMethodKind());
    }

    @Test
    public void extractsCorrectMethodName() {
        String name = "testMethod";
        IMethodName methodName = createMethodNameMock(name, false, Visibility.PUBLIC);

        MethodProperties properties = new MethodProperties(methodName);

        assertEquals(name, properties.getMethodName());
    }

    private IMethodName createMethodNameMock(String name, boolean isStatic, Visibility visibility) {
        IMethodName methodName = mock(IMethodName.class);
        when(methodName.getFullName()).thenReturn(name);
        when(methodName.isStatic()).thenReturn(isStatic);

        return methodName;
    }
}