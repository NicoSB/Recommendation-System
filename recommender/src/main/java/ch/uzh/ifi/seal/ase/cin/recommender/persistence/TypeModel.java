package ch.uzh.ifi.seal.ase.cin.recommender.persistence;

import ch.uzh.ifi.seal.ase.cin.recommender.model.DatabaseEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TypeModel {
    private String type;
    private List<DatabaseEntry> entries = new ArrayList<>();

    public TypeModel(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEntries(List<DatabaseEntry> entries) {
        this.entries = entries;
    }

    public List<DatabaseEntry> getEntries() {
        return entries;
    }

    public void insertPairOrIncreaseFrequency(QuerySelectionPair pair) {
        if (contains(pair)) {
            increaseFrequency(pair);
        } else {
            entries.add(new DatabaseEntry(pair, 1));
        }
    }

    private void increaseFrequency(QuerySelectionPair pair) {
        DatabaseEntry entry = findEntry(pair);
        entry.setFrequency(entry.getFrequency() + 1);
    }

    public boolean contains(QuerySelectionPair pair) {
        return findEntry(pair).getFrequency() != 0;
    }

    private DatabaseEntry findEntry(QuerySelectionPair pair) {
        return entries.stream()
                .filter(e -> e.getQuery().equals(pair.getQuery()) && e.getSelection().equals(pair.getSelection()))
                .findFirst()
                .orElse(new DatabaseEntry(pair, 0));
    }

    public int size() {
        return entries.size();
    }

    public long getFrequency(QuerySelectionPair pair) {
        return findEntry(pair).getFrequency();
    }

    public DatabaseEntry getEntry(QuerySelectionPair pair) {
        return new DatabaseEntry(pair, getFrequency(pair));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeModel typeModel = (TypeModel) o;
        return Objects.equals(type, typeModel.type) &&
                entries.equals(typeModel.entries);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type, entries);
    }
}
