package ch.uzh.ifi.seal.ase.cin.miner;

import ch.uzh.ifi.seal.ase.cin.recommender.persistence.QuerySelectionPair;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.TypeModel;

public class RepositoryUpdater {
    private InMemoryRepository repository;

    public RepositoryUpdater(InMemoryRepository repository) {
        this.repository = repository;
    }

    public void insertPair(QuerySelectionPair pair) {
        if (pair.getQuery() == null || pair.getSelection() == null)
            return;

        TypeModel model = repository.find(pair.getQuery().getType());
        if (model == null) {
            model = new TypeModel(pair.getQuery().getType());
        }

        model.insertPairOrIncreaseFrequency(pair);
        repository.save(model);
    }

    public void store() {
        repository.store();
        repository.clear();
    }
}
