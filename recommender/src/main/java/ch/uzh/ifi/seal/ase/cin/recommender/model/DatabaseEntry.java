package ch.uzh.ifi.seal.ase.cin.recommender.model;

import cc.kave.commons.model.naming.IName;

public class DatabaseEntry {
    private Query query;
    private IName selection;
    private long frequency;

    public DatabaseEntry(Query query, IName selection, long frequency) {
        this.query = query;
        this.selection = selection;
        this.frequency = frequency;
    }

    public Query getQuery() {
        return query;
    }

    public IName getSelection() {
        return selection;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public void setSelection(IName selection) {
        this.selection = selection;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }
}
