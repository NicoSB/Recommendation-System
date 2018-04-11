package ch.uzh.ifi.seal.ase.cin.recommender.model;

import cc.kave.commons.model.naming.IName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseEntryTest {

    @Mock
    private IName name;

    @Test
    public void WhenInstancesAreSame_EqualsIsTrue() {
        DatabaseEntry entry = new DatabaseEntry(new Query("Type"), name, 0);

        assertEquals(entry, entry);
    }

    @Test
    public void WhenInstancesAreSame_HashCodesAreEqual() {
        DatabaseEntry entry = new DatabaseEntry(new Query("Type"), name, 0);

        assertEquals(entry.hashCode(), entry.hashCode());
    }

    @Test
    public void WhenInstancesAreEqual_EqualsIsTrue() {
        Query query = new Query("Type");
        int frequency = 0;

        DatabaseEntry entry1 = new DatabaseEntry(query, name, frequency);
        DatabaseEntry entry2 = new DatabaseEntry(query, name, frequency);

        assertEquals(entry1, entry2);
    }

    @Test
    public void WhenInstancesAreEqual_HashCodesAreEqual() {
        Query query = new Query("Type");
        int frequency = 0;

        DatabaseEntry entry1 = new DatabaseEntry(query, name, frequency);
        DatabaseEntry entry2 = new DatabaseEntry(query, name, frequency);

        assertEquals(entry1.hashCode(), entry2.hashCode());
    }

    @Test
    public void WhenQueriesAreDifferent_EqualsIsFalse() {
        Query query1 = new Query("Type1");
        Query query2 = new Query("Type2");
        int frequency = 0;

        DatabaseEntry entry1 = new DatabaseEntry(query1, name, frequency);
        DatabaseEntry entry2 = new DatabaseEntry(query2, name, frequency);

        assertNotEquals(entry1, entry2);
    }

    @Test
    public void WhenQueriesAreDifferent_HashCodesAreNotEqual() {
        Query query1 = new Query("Type1");
        Query query2 = new Query("Type2");
        int frequency = 0;

        DatabaseEntry entry1 = new DatabaseEntry(query1, name, frequency);
        DatabaseEntry entry2 = new DatabaseEntry(query2, name, frequency);

        assertNotEquals(entry1.hashCode(), entry2.hashCode());
    }

    @Test
    public void WhenNamesAreDifferent_EqualsIsFalse() {
        IName name2 = Mockito.mock(IName.class);
        Query query = new Query("Type");
        int frequency = 0;

        DatabaseEntry entry1 = new DatabaseEntry(query, name, frequency);
        DatabaseEntry entry2 = new DatabaseEntry(query, name2, frequency);

        assertNotEquals(entry1, entry2);
    }

    @Test
    public void WhenNamesAreDifferent_HashCodesAreNotEqual() {
        IName name2 = Mockito.mock(IName.class);
        Query query = new Query("Type");
        int frequency = 0;

        DatabaseEntry entry1 = new DatabaseEntry(query, name, frequency);
        DatabaseEntry entry2 = new DatabaseEntry(query, name2, frequency);

        assertNotEquals(entry1.hashCode(), entry2.hashCode());
    }

    @Test
    public void WhenFrequenciesAreDifferent_EqualsIsFalse() {
        Query query = new Query("Type");
        int frequency1 = 1;
        int frequency2 = 2;

        DatabaseEntry entry1 = new DatabaseEntry(query, name, frequency1);
        DatabaseEntry entry2 = new DatabaseEntry(query, name, frequency2);

        assertNotEquals(entry1, entry2);
    }

    @Test
    public void WhenFrequenciesAreDifferent_HashCodesAreNotEqual() {
        Query query = new Query("Type");
        int frequency1 = 1;
        int frequency2 = 2;

        DatabaseEntry entry1 = new DatabaseEntry(query, name, frequency1);
        DatabaseEntry entry2 = new DatabaseEntry(query, name, frequency2);

        assertNotEquals(entry1.hashCode(), entry2.hashCode());
    }

}