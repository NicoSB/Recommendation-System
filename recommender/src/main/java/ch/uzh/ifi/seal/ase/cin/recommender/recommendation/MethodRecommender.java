package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.rsse.calls.datastructures.Tuple;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.TypeModel;

import java.util.Set;

public interface MethodRecommender {
    Set<Tuple<IMethodName, Double>> getRecommendations(TypeModel typeModel, Query query);
}
