package ch.uzh.ifi.seal.ase.cin.recommender;

import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.utils.io.IReadingArchive;
import cc.kave.commons.utils.io.ReadingArchive;
import cc.kave.rsse.calls.datastructures.Tuple;
import ch.uzh.ifi.seal.ase.cin.recommender.model.*;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.ModelRepository;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.OnDemandModelRepository;
import ch.uzh.ifi.seal.ase.cin.recommender.recommendation.BasicMethodRecommender;
import ch.uzh.ifi.seal.ase.cin.recommender.recommendation.MethodRecommender;
import ch.uzh.ifi.seal.ase.cin.recommender.recommendation.RecommendationSystem;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Example {

    private final static String eventsDir = "C:\\Users\\ncstr\\Downloads\\Events-170301\\2016-05-09";

    public static void main(String[] args) {
        Path modelPath = Paths.get("C:\\Users\\ncstr\\Model");
        ModelRepository repository = new OnDemandModelRepository(modelPath);
        MethodRecommender recommender = new BasicMethodRecommender();

        RecommendationSystem recommendationSystem = new RecommendationSystem(repository, recommender);
//        List<Context> contexts = processZips();
//        contexts.forEach(c -> System.out.println(recommendationSystem.query(c)));
        query(recommendationSystem);
    }

    private static void query(RecommendationSystem recommendationSystem) {
        Query q = new Query("Test");
        q.setEnclosingNodeKind(EnclosingNodeKind.WHILE);
        String[] types = {"Test2"};
        q.setExpectedTypes(types);
        q.setEnclosingMethodVisibility(Visibility.PUBLIC);
        q.setCompletionNodeKind(CompletionNodeKind.RETURN_VALUE);
        q.setEnclosingMethodKind(MethodKind.INSTANCE);

        Set<Tuple<IMethodName, Double>> results = recommendationSystem.query(q);
        System.out.println("Results before: ");
        System.out.println(results);

//        recommendationSystem.updateModel(q, );
        results = recommendationSystem.query(q);
        System.out.println("Results after: ");
        System.out.println(results);
    }

    private static List<Context> processZips() {
        Set<String> userZips = FileUtils.findAllZips(eventsDir);
        List<Context> contexts = new ArrayList<>();

        for (String userZip : userZips) {
            System.out.printf("\n#### processing user zip: %s #####\n", userZip);
            contexts.addAll(processUserZip(userZip));
        }

        return contexts;
    }

    private static List<Context> processUserZip(String userZip) {
        int numProcessedEvents = 0;
        List<Context> contexts = new ArrayList<>();
        // open the .zip file ...
        try (IReadingArchive ra = new ReadingArchive(new File(eventsDir, userZip))) {
            // ... and iterate over content.
            // the iteration will stop after 200 events to speed things up, remove this
            // guard to process all events.
            while (ra.hasNext() && (numProcessedEvents++ < 20000)) {
                /*
                 * within the userZip, each stored event is contained as a single file that
                 * contains the Json representation of a subclass of IDEEvent.
                 */
                IIDEEvent e = ra.getNext(IIDEEvent.class);
                if (e instanceof CompletionEvent && ((CompletionEvent) e).context != null)
                    contexts.add(((CompletionEvent) e).context);
            }
        }

        return contexts;
    }
}
