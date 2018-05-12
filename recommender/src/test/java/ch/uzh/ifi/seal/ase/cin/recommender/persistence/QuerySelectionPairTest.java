package ch.uzh.ifi.seal.ase.cin.recommender.persistence;

import cc.kave.commons.model.naming.IGenericName;
import cc.kave.commons.model.naming.Names;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.commons.model.naming.types.ITypeParameterName;
import cc.kave.commons.pointsto.analysis.utils.GenericNameUtils;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import sun.net.www.content.text.Generic;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class QuerySelectionPairTest {

    @Mock
    private MethodName name;

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
        MethodName name2 = Mockito.mock(MethodName.class);

        QuerySelectionPair pair1 = new QuerySelectionPair(query, name);
        QuerySelectionPair pair2 = new QuerySelectionPair(query, name2);

        assertNotEquals(pair1, pair2);

    }

    @Test
    public void WhenSelectionsAreDifferent_HashCodesAreNotEqual() {
        Query query = new Query("Type");
        MethodName name2 = Mockito.mock(MethodName.class);

        QuerySelectionPair pair1 = new QuerySelectionPair(query, name);
        QuerySelectionPair pair2 = new QuerySelectionPair(query, name2);

        assertNotEquals(pair1.hashCode(), pair2.hashCode());
    }

    @Test
    public void removesGenerics() {
        MethodName methodName = (MethodName) Names.newMethod("M:static [TSource] [System.Linq.Enumerable, System.Core, 4.0.0.0].FirstOrDefault`1[[TSource -> ACAT.Lib.Core.ActuatorManagement.ActuatorEx, Core]](this [i:System.Collections.Generic.IEnumerable`1[[T]], mscorlib, 4.0.0.0] source, [d:[TResult] [System.Func`2[[T],[TResult]], mscorlib, 4.0.0.0].([T] arg)] predicate)");
        Query query = new Query("Test");
        QuerySelectionPair pair = new QuerySelectionPair(query, methodName);

        assertEquals("M:static [TSource] [System.Linq.Enumerable, System.Core, 4.0.0.0].FirstOrDefault`1[[TSource]](this [i:System.Collections.Generic.IEnumerable`1[[T]], mscorlib, 4.0.0.0] source, [d:[TResult] [System.Func`2[[T],[TResult]], mscorlib, 4.0.0.0].([T] arg)] predicate)", pair.getSelection().getIdentifier());
    }

}