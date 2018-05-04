package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.impl.expressions.assignable.CompletionExpression;
import cc.kave.commons.utils.ssts.completioninfo.CompletionInfo;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;

import java.util.Optional;

public class QueryExtractor {

    public static Query extractQuery(ISST sst) {
        Optional<CompletionInfo> completionInfoOptional = CompletionInfo.extractCompletionInfoFrom(sst);

        if (!completionInfoOptional.isPresent())
            return null;

        return extractQuery(completionInfoOptional.get(), sst);
    }

    private static Query extractQuery(CompletionInfo completionInfo, ISST sst) {
        Query query = new Query(completionInfo.getTriggeredType().getFullName());

        String[] expectedTypes = extractExpectedTypes(completionInfo);
        query.setExpectedTypes(expectedTypes);

        setSSTDependantFields(query, sst);

        return query;
    }

    private static String[] extractExpectedTypes(CompletionInfo completionInfo) {
        if (!completionInfo.hasExpectedType())
            return new String[]{""};

        return new String[]{completionInfo.getExpectedType().getFullName()};
    }

    private static void setSSTDependantFields(Query query, ISST sst) {
        EnclosingMethodTrackingVisitor visitor = new EnclosingMethodTrackingVisitor();
        EnclosingMethodTrackingWalker walker = new EnclosingMethodTrackingWalker();
        walker.registerVisitor(visitor);

        walker.walk(sst);
        MethodProperties properties = visitor.getFirstOfClass(CompletionExpression.class);

        query.setEnclosingMethodKind(properties.getMethodKind());
        query.setEnclosingMethodVisibility(properties.getVisibility());
    }

}
