package ch.uzh.ifi.seal.ase.cin.miner;

import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.utils.ssts.completioninfo.CompletionInfo;
import ch.uzh.ifi.seal.ase.cin.miner.sst.InvocationToCompletionConvertingVisitor;
import ch.uzh.ifi.seal.ase.cin.miner.sst.MultipleCompletionInfoVisitor;
import ch.uzh.ifi.seal.ase.cin.miner.sst.SelectionCompletionInfo;
import ch.uzh.ifi.seal.ase.cin.recommender.model.CompletionNodeKind;
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
            ISST converted = replaceInvocationExpressions(sst);
            if (converted == null)
                return;

            List<SelectionCompletionInfo> completionInfos = extractCompletionInfos(converted);
            insertQuerySelectionPairsIntoDatabase(completionInfos, converted);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    private List<SelectionCompletionInfo> extractCompletionInfos(ISST sst) {
        MultipleCompletionInfoVisitor visitor = new MultipleCompletionInfoVisitor();
        sst.accept(visitor, null);

        return visitor.getCompletionInfos();
    }

    private ISST replaceInvocationExpressions(ISST sst) {
        InvocationToCompletionConvertingVisitor visitor = new InvocationToCompletionConvertingVisitor();
        ISST converted = (ISST) sst.accept(visitor, null);

        return  visitor.hasCompletionExpression() ? converted : null;
    }

    private void insertQuerySelectionPairsIntoDatabase(List<SelectionCompletionInfo> completionInfos, ISST sst) {
        for (SelectionCompletionInfo info : completionInfos) {
            Query query = extractQuery(info, sst);
            QuerySelectionPair pair = new QuerySelectionPair(query, info.getSelection());

            if(!pair.getQuery().isTypeUnknown())
               updater.insertPair(pair);
        }
    }
}
