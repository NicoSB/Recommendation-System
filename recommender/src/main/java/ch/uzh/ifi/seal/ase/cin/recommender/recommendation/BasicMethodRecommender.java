package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.rsse.calls.datastructures.Tuple;
import ch.uzh.ifi.seal.ase.cin.recommender.model.DatabaseEntry;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.TypeModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicMethodRecommender implements MethodRecommender {
    @Override
    public Set<Tuple<IMethodName, Double>> getRecommendations(TypeModel typeModel, Query query) {
        List<DatabaseEntry> entries = typeModel.getEntries().stream()
                .filter(pair -> pair.getQuery().equals(query))
                .collect(Collectors.toList());

        if (entries.isEmpty())
            return new HashSet<>();

        int totalFrequency = calculateTotalFrequency(entries);
        Set<Tuple<IMethodName, Double>> methods = new HashSet<>();

        for (DatabaseEntry entry : entries) {
            methods.add(Tuple.newTuple(entry.getSelection(), (double) entry.getFrequency() / totalFrequency));
        }

        return methods;
    }

    private int calculateTotalFrequency(List<DatabaseEntry> entries) {
        int total = 0;

        for (DatabaseEntry entry : entries) {
            total += entry.getFrequency();
        }

        return total;
    }
}
