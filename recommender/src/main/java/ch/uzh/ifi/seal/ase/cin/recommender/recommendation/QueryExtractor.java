package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.impl.expressions.assignable.CompletionExpression;
import cc.kave.commons.model.ssts.visitor.ISSTNode;
import cc.kave.commons.pointsto.analysis.utils.EnclosingNodeHelper;
import cc.kave.commons.utils.ssts.SSTNodeHierarchy;
import cc.kave.commons.utils.ssts.completioninfo.CompletionInfo;
import ch.uzh.ifi.seal.ase.cin.recommender.model.EnclosingNodeKind;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import ch.uzh.ifi.seal.ase.cin.recommender.util.SSTUtils;
import ch.uzh.ifi.seal.ase.cin.recommender.util.StatementToNodeKindMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class QueryExtractor {

    private static Logger logger = LogManager.getLogger(QueryExtractor.class);

    public static Query extractQuery(ISST sst) {
        Optional<CompletionInfo> completionInfoOptional = CompletionInfo.extractCompletionInfoFrom(sst);

        if (!completionInfoOptional.isPresent()) {
            logger.warn("No completion info is present in provided SST!");
            return null;
        }

        return extractQuery(completionInfoOptional.get(), sst);
    }

    private static Query extractQuery(CompletionInfo completionInfo, ISST sst) {
        if (completionInfo.getTriggeredType() == null) {
            logger.error("Can not process completion info without a triggered type!");
            return null;
        }

        Query query = new Query(completionInfo.getTriggeredType().getFullName());

        String[] expectedTypes = extractExpectedTypes(completionInfo);
        query.setExpectedTypes(expectedTypes);

        SSTNodeHierarchy hierarchy = new SSTNodeHierarchy(sst);
        CompletionExpression completionExpression = SSTUtils.findFirst(sst, CompletionExpression.class);
        EnclosingNodeKind nodeKind = getEnclosingNodeKind(hierarchy, completionExpression);
        query.setEnclosingNodeKind(nodeKind);

        setSSTDependantFields(query, sst);

        return query;
    }

    private static EnclosingNodeKind getEnclosingNodeKind(SSTNodeHierarchy hierarchy, ISSTNode node) {
        ISSTNode enclosingNode = hierarchy.getParent(node);
        EnclosingNodeKind nodeKind = StatementToNodeKindMapper.map(enclosingNode);
        if (enclosingNode != null && nodeKind == EnclosingNodeKind.DEFAULT)
            return getEnclosingNodeKind(hierarchy, enclosingNode);

        return nodeKind;
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
