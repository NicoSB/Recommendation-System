package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.naming.IName;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.rsse.calls.ICallsRecommender;
import cc.kave.rsse.calls.datastructures.Tuple;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.ModelRepository;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.QuerySelectionPair;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.TypeModel;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Set;
public class RecommendationSystem implements ICallsRecommender<Query> {

    private ModelRepository repository;
    private MethodRecommender recommender;
    Logger logger = LogManager.getLogger(RecommendationSystem.class);

    public RecommendationSystem(ModelRepository repository, MethodRecommender recommender) {
        this.repository = repository;
        this.recommender = recommender;
    }

    @Override
    public Set<Tuple<IMethodName, Double>> query(Query query) {
        if (query == null) {
            logger.warn("Query is null!");
            return Sets.newHashSet();
        }

        logger.info("Processing query: " + query.toString());

        String modelId = query.getType();
        TypeModel typemodel = getOrCreateTypeModel(modelId);

        return recommender.getRecommendations(typemodel, query);
    }

    @Override
    public Set<Tuple<IMethodName, Double>> query(Context context) {
        Query query = convertContext(context);

        return query(query);
    }

    @Override
    public Set<Tuple<IMethodName, Double>> query(Context context, List<IName> list) {
        Query query = convertContext(context);

        return query(query);
    }

    private Query convertContext(Context context) {
        return QueryExtractor.extractQuery(context.getSST());
    }

    @Override
    public int getSize() {
        return 0;
    }

    public void updateModel(Query query, String selection) {
        String modelId = query.getType();
        TypeModel typeModel = getOrCreateTypeModel(modelId);
        QuerySelectionPair pair = new QuerySelectionPair(query, selection);

        typeModel.insertPairOrIncreaseFrequency(pair);

        repository.save(typeModel);
    }

    private TypeModel getOrCreateTypeModel(String modelId) {
        return Optional.ofNullable(repository.find(modelId)).orElseGet(() -> new TypeModel(modelId));
    }
}
