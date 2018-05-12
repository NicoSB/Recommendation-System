package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.rsse.calls.datastructures.Tuple;
import ch.uzh.ifi.seal.ase.cin.SSTTestUtils;
import ch.uzh.ifi.seal.ase.cin.recommender.model.DatabaseEntry;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.QuerySelectionPair;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.TypeModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasicMethodRecommenderTest {

    public static final String TYPE_NAME = "Test";
    BasicMethodRecommender sut;

    @Mock
    private TypeModel typeModel;
    private List<DatabaseEntry> entries;

    @Before
    public void setUp() {
        sut = new BasicMethodRecommender();
        entries = new ArrayList<>();

        when(typeModel.getEntries()).thenReturn(entries);
    }

    private DatabaseEntry createDatabaseEntry(String type, String selection, int frequency) {
        MethodName methodName = new MethodName(selection);
        QuerySelectionPair pair = new QuerySelectionPair(new Query(type), methodName);
        return new DatabaseEntry(pair, frequency);
    }

    @Test
    public void calculatesFrequencyRatio() {
        Query query = new Query(TYPE_NAME);

        DatabaseEntry entry1 = createDatabaseEntry(TYPE_NAME, "Selection", 9);
        DatabaseEntry entry2 = createDatabaseEntry(TYPE_NAME, "Selection", 1);

        entries.add(entry1);
        entries.add(entry2);

        Set<Tuple<IMethodName, Double>> recommendations = sut.getRecommendations(typeModel, query);
        Tuple<IMethodName, Double> tuple1 = (Tuple<IMethodName, Double>) recommendations.toArray()[0];
        Tuple<IMethodName, Double> tuple2 = (Tuple<IMethodName, Double>) recommendations.toArray()[1];

        assertEquals(0.1d, tuple1.getSecond(), 0.0d);
        assertEquals(0.9d, tuple2.getSecond(), 0.0d);
    }
    
    @Test
    public void onlySelectsMatchinQueries() {
        Query query = new Query("not" + TYPE_NAME);

        DatabaseEntry entry1 = createDatabaseEntry(TYPE_NAME, "Selection", 9);
        DatabaseEntry entry2 = createDatabaseEntry(TYPE_NAME, "Selection", 1);

        entries.add(entry1);
        entries.add(entry2);

        Set<Tuple<IMethodName, Double>> recommendations = sut.getRecommendations(typeModel, query);

        assertEquals(0, recommendations.size());
    }
}