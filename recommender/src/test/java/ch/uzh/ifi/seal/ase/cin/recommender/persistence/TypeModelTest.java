package ch.uzh.ifi.seal.ase.cin.recommender.persistence;

import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import ch.uzh.ifi.seal.ase.cin.recommender.model.DatabaseEntry;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TypeModelTest {

    private TypeModel _sut;

    @Mock
    private MethodName name;

    @Before
    public void setUp() {
        _sut = new TypeModel("Test");
    }

    @Test
    public void WhenEntryDoesNotExist_AddsEntry() {
        Query query = new Query(_sut.getType());
        QuerySelectionPair pair = new QuerySelectionPair(query, name);

        _sut.insertPairOrIncreaseFrequency(pair);

        assertEquals(1, _sut.size());
        assertTrue(_sut.contains(pair));
        assertEquals(1, _sut.getFrequency(pair));
    }

    @Test
    public void WhenPairIsNotStored_ReturnsZero() {
        Query query = new Query(_sut.getType());
        QuerySelectionPair pair = new QuerySelectionPair(query, name);

        assertEquals(0, _sut.getFrequency(pair));
    }

    @Test
    public void ReturnsCorrectDatabaseEntry() {
        Query query = new Query(_sut.getType());
        QuerySelectionPair pair = new QuerySelectionPair(query, name);
        DatabaseEntry expected = new DatabaseEntry(pair, 1);

        _sut.insertPairOrIncreaseFrequency(pair);

        assertEquals(expected, _sut.getEntry(pair));
    }

    @Test
    public void WhenEntryExists_IncreasesFrequency() {
        Query query = new Query(_sut.getType());
        QuerySelectionPair pair = new QuerySelectionPair(query, name);

        _sut.insertPairOrIncreaseFrequency(pair);
        _sut.insertPairOrIncreaseFrequency(pair);

        assertEquals(1, _sut.getEntries().size());
        assertTrue(_sut.contains(pair));
        assertEquals(2, _sut.getFrequency(pair));
    }

    @Test
    public void WhenPairWasAdded_ContainsIsTrue() {
        Query query = new Query(_sut.getType());
        QuerySelectionPair pair = new QuerySelectionPair(query, name);

        _sut.insertPairOrIncreaseFrequency(pair);

        assertTrue(_sut.contains(pair));
    }


    @Test
    public void WhenPairWasNotAdded_ContainsIsFalse() {
        Query query = new Query(_sut.getType());
        QuerySelectionPair pair = new QuerySelectionPair(query, name);

        assertFalse(_sut.contains(pair));
    }

    @Test
    public void WhenInstancesAreEqual_EqualsIsTrue() {
        Query query = new Query(_sut.getType());
        QuerySelectionPair pair = new QuerySelectionPair(query, name);

        TypeModel model1 = new TypeModel(_sut.getType());
        model1.insertPairOrIncreaseFrequency(pair);

        TypeModel model2 = new TypeModel(_sut.getType());
        model2.insertPairOrIncreaseFrequency(pair);

        assertEquals(model1, model2);
    }

}