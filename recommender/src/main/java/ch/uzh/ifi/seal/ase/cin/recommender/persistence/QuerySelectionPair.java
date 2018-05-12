package ch.uzh.ifi.seal.ase.cin.recommender.persistence;

import cc.kave.commons.model.naming.Names;
import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.commons.pointsto.analysis.utils.GenericNameUtils;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import ch.uzh.ifi.seal.ase.cin.recommender.util.SSTUtils;
import sun.net.www.content.text.Generic;

import java.util.Objects;

public class QuerySelectionPair {
    private Query query;
    private MethodName selection;

    public QuerySelectionPair(Query query, MethodName selection) {
        this.query = query;
        this.selection = (MethodName) SSTUtils.removeGenerics(selection);;
    }


    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public MethodName getSelection() {
        return selection;
    }

    public void setSelection(MethodName selection) {
        this.selection = selection;
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
