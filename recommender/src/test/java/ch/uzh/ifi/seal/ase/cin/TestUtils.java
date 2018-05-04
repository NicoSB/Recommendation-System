package ch.uzh.ifi.seal.ase.cin;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class TestUtils {

    public static void assertContains(Object[] objects, Object obj) {
        assertTrue(Arrays.asList(objects).contains(obj));
    }

    public static void assertContains(Collection collection, Object obj) {
        assertTrue(collection.contains(obj));
    }
}
