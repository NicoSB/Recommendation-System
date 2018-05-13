package ch.uzh.ifi.seal.ase.cin.miner;

import ch.uzh.ifi.seal.ase.cin.recommender.persistence.ModelRepository;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.OnDemandModelRepository;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.TypeModel;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository implements ModelRepository {
    private Map<String, TypeModel> models = new HashMap<>();
    private OnDemandModelRepository persistentRepository;

    public InMemoryRepository(Path directory) {
        persistentRepository = new OnDemandModelRepository(directory);
    }

    @Override
    public String save(TypeModel model) {
        models.put(model.getType(), model);

        return model.getType();
    }

    @Override
    public TypeModel find(String id) {
        if (!models.containsKey(id))
            models.put(id, persistentRepository.find(id));

        return models.get(id);
    }

    public void store() {
        models.forEach((key, value) -> persistentRepository.save(value));
    }

    public void clear() {
        models.clear();
    }
}
