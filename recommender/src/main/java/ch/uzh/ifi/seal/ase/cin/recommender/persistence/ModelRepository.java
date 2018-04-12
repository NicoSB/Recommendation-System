package ch.uzh.ifi.seal.ase.cin.recommender.persistence;

public interface ModelRepository {
    String save(TypeModel model);
    TypeModel find(String id);
}
