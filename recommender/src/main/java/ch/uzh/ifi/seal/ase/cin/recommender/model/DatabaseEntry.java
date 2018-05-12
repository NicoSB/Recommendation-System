package ch.uzh.ifi.seal.ase.cin.recommender.model;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.QuerySelectionPair;
import com.google.gson.annotations.Expose;

import java.util.Objects;

public class DatabaseEntry {

    private QuerySelectionPair pair;
    private long frequency;

    public DatabaseEntry(Query query, MethodName selection, long frequency) {
        pair = new QuerySelectionPair(query, selection);
        this.frequency = frequency;
    }

    public DatabaseEntry(QuerySelectionPair pair, long frequency) {
        this.pair = pair;
        this.frequency = frequency;


    }

    public Query getQuery() {
        return pair.getQuery();
    }

    public MethodName getSelection() {
        return pair.getSelection();
    }

    public long getFrequency() {
        return frequency;
    }

    public QuerySelectionPair getPair() {
        return pair;
    }

    public void setPair(QuerySelectionPair pair) {
        this.pair = pair;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseEntry that = (DatabaseEntry) o;
        return frequency == that.frequency &&
                Objects.equals(pair, that.pair);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pair, frequency);
    }
}
