package ch.uzh.ifi.seal.ase.cin.recommender.persistence;

import cc.kave.commons.model.naming.IName;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;

import java.util.Objects;

public class QuerySelectionPair {
    private Query query;
    private String selection;

    public QuerySelectionPair(Query query, IName selection) {
        this.query = query;
        this.selection = selection.toString();
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(IName selection) {
        this.selection = selection.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuerySelectionPair that = (QuerySelectionPair) o;
        return Objects.equals(query, that.query) &&
                Objects.equals(selection, that.selection);
    }

    @Override
    public int hashCode() {

        return Objects.hash(query, selection);
    }
}
