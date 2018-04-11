package ch.uzh.ifi.seal.ase.cin.recommender.persistence;

import cc.kave.commons.model.naming.IName;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class QuerySelectionPairTest {

    @Mock
    private IName name;

    @Test
    public void WhenInstancesAreSame_EqualsIsTrue() {
        Query query = new Query("Type");
        QuerySelectionPair pair = new QuerySelectionPair(query, name);

        assertEquals(pair, pair);
    }

    @Test
    public void WhenInstancesAreSame_HashCodesAreEqual() {
        Query query = new Query("Type");
        QuerySelectionPair pair = new QuerySelectionPair(query, name);

        assertEquals(pair.hashCode(), pair.hashCode());
    }

    @Test
    public void WhenInstancesAreEqual_EqualsIsTrue() {
        Query query = new Query("Type");
        QuerySelectionPair pair1 = new QuerySelectionPair(query, name);
        QuerySelectionPair pair2 = new QuerySelectionPair(query, name);

        assertEquals(pair1, pair2);

    }

    @Test
    public void WhenInstancesAreEqual_HashCodesAreEqual() {
        Query query = new Query("Type");
        QuerySelectionPair pair1 = new QuerySelectionPair(query, name);
        QuerySelectionPair pair2 = new QuerySelectionPair(query, name);

        assertEquals(pair1.hashCode(), pair2.hashCode());
    }

    @Test
    public void WhenQueriesAreDifferent_EqualsIsFalse() {
        Query query1 = new Query("Type1");
        Query query2 = new Query("Type2");

        QuerySelectionPair pair1 = new QuerySelectionPair(query1, name);
        QuerySelectionPair pair2 = new QuerySelectionPair(query2, name);

        assertNotEquals(pair1, pair2);

    }

    @Test
    public void WhenQueriesAreDifferent_HashCodesAreNotEqual() {
        Query query1 = new Query("Type1");
        Query query2 = new Query("Type2");

        QuerySelectionPair pair1 = new QuerySelectionPair(query1, name);
        QuerySelectionPair pair2 = new QuerySelectionPair(query2, name);

        assertNotEquals(pair1.hashCode(), pair2.hashCode());
    }


    @Test
    public void WhenSelectionsAreDifferent_EqualsIsFalse() {
        Query query = new Query("Type");
        IName name2 = Mockito.mock(IName.class);

        QuerySelectionPair pair1 = new QuerySelectionPair(query, name);
        QuerySelectionPair pair2 = new QuerySelectionPair(query, name2);

        assertNotEquals(pair1, pair2);

    }

    @Test
    public void WhenSelectionsAreDifferent_HashCodesAreNotEqual() {
        Query query = new Query("Type");
        IName name2 = Mockito.mock(IName.class);

        QuerySelectionPair pair1 = new QuerySelectionPair(query, name);
        QuerySelectionPair pair2 = new QuerySelectionPair(query, name2);

        assertNotEquals(pair1.hashCode(), pair2.hashCode());
    }
}