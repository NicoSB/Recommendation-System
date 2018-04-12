package ch.uzh.ifi.seal.ase.cin.recommender.persistence;

import ch.uzh.ifi.seal.ase.cin.recommender.persistence.utils.JsonUtilities;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OnDemandModelRepository implements ModelRepository {

    private static final String JSON_POSTFIX = ".json";
    private Path directory;

    public OnDemandModelRepository(Path directory) {
        this.directory = directory;
    }

    @Override
    public String save(TypeModel model) {
        persist(model);

        return model.getType();
    }

    private void persist(TypeModel model) {
        try {
            JsonUtilities.store(directory.toString(), model);
        } catch (IOException e) {
            System.err.println(String.format("%s could not be saved to the file system!", model.getType()));
        }
    }

    @Override
    public TypeModel find(String id) {
        try {
            String path = Paths.get(directory.toString(), id + JSON_POSTFIX).toString();
            return JsonUtilities.load(path);
        } catch (RuntimeException e) {
            return null;
        }
    }
}
