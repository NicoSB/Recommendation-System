package ch.uzh.ifi.seal.ase.cin.miner;

import cc.kave.commons.model.ssts.ISST;
import ch.uzh.ifi.seal.ase.cin.miner.sst.InvocationToCompletionConvertingVisitor;
import ch.uzh.ifi.seal.ase.cin.miner.sst.MultipleCompletionInfoVisitor;
import ch.uzh.ifi.seal.ase.cin.miner.sst.SelectionCompletionInfo;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.QuerySelectionPair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static ch.uzh.ifi.seal.ase.cin.recommender.recommendation.QueryExtractor.extractQuery;

public class SSTProcessor {
    private Logger logger = LogManager.getLogger(SSTProcessor.class);
    private RepositoryUpdater updater;

    public SSTProcessor(RepositoryUpdater updater) {
        this.updater = updater;
    }

    public void processSST(ISST sst) {
        try {
            ISST converted = convertSST(sst);
            insertQuerySelectionPairsIntoDatabase(converted);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    private ISST convertSST(ISST sst) {
        InvocationToCompletionConvertingVisitor visitor = new InvocationToCompletionConvertingVisitor();

        return (ISST) sst.accept(visitor, null);
    }

    private void insertQuerySelectionPairsIntoDatabase(ISST sst) {
        MultipleCompletionInfoVisitor visitor = new MultipleCompletionInfoVisitor();
        sst.accept(visitor, null);

        List<QuerySelectionPair> pairs = new ArrayList<>();
        List<SelectionCompletionInfo> completionInfos = visitor.getCompletionInfos();
        for (SelectionCompletionInfo info : completionInfos) {
            Query query = extractQuery(info, sst);
            QuerySelectionPair pair = new QuerySelectionPair(query, info.getSelection());
            updater.insertPair(pair);
        }
    }
}
