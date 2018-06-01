package ch.uzh.ifi.seal.ase.cin.evaluation.evaluation;

import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.model.events.completionevents.IProposal;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.utils.io.IReadingArchive;
import cc.kave.commons.utils.io.ReadingArchive;
import cc.kave.rsse.calls.datastructures.Tuple;
import ch.uzh.ifi.seal.ase.cin.miner.utils.FileUtils;
import ch.uzh.ifi.seal.ase.cin.recommender.recommendation.RecommendationSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Evaluator {
    private static final String METHOD_PATTERN = "\\w+\\(.*\\)";
    private static final int MAX_PROPOSALS = 10;

    private final Logger logger = LogManager.getLogger(Evaluator.class);

    private String inputDirectory;
    private RecommendationSystem recommendationSystem;
    private int totalEvents = 0;
    private int evaluableEvents = 0;
    private int top1 = 0;
    private int topX = 0;
    private int nameTop1 = 0;
    private int nameTopX = 0;

    public Evaluator(String inputDirectory, RecommendationSystem recommendationSystem) {
        this.inputDirectory = inputDirectory;
        this.recommendationSystem = recommendationSystem;
    }

    public void run() {
        Set<String> zips = FileUtils.findAllZips(inputDirectory);

        zips.forEach(zip -> evaluateZip(Paths.get(inputDirectory, zip).toString()));
    }

    private void evaluateZip(String zipUri) {
        IReadingArchive readingArchive = new ReadingArchive(new File(zipUri));

        while (readingArchive.hasNext()) {
            IIDEEvent event = readingArchive.getNext(IIDEEvent.class);
            if (event instanceof ICompletionEvent) {
                try {
                    evaluateEvent((ICompletionEvent) event);
                } catch (Exception e) {
                    logger.warn("Could not evaluate event: {}", e.getMessage());
                }
            }
        }
    }

    private void evaluateEvent(ICompletionEvent event) {
        totalEvents++;

        Context context = event.getContext();
        IProposal selection = event.getLastSelectedProposal();
        if (selection == null || selection.getName() == null) {
            return;
        }

        Set<Tuple<IMethodName, Double>> recommendations = recommendationSystem.query(context);


        if (recommendations.isEmpty()) {
            return;
        }

        evaluate(selection, recommendations);

    }

    private void evaluate(IProposal selection, Set<Tuple<IMethodName, Double>> recommendations) {
        StringBuilder builder = new StringBuilder();
        String method = getMethod(selection.getName().getIdentifier());
        builder.append(method);

        evaluableEvents++;

        List<String> filtered = recommendations.stream()
                .sorted(Comparator.comparingDouble((Tuple t) -> (double) t.getSecond()).reversed())
                .limit(MAX_PROPOSALS)
                .map(t -> getMethod(t.getFirst().getIdentifier()))
                .collect(Collectors.toList());

        evaluateMethod(method, filtered);
        evaluateMethodName(method, filtered);

        filtered.forEach(name -> {
            builder.append(";");
            builder.append(name);
        });

        logger.info(builder.toString());
    }

    private void evaluateMethod(String methodName, List<String> filtered) {
        if (methodName.equals(filtered.get(0))) {
            top1++;
        }

        if (filtered.contains(methodName)) {
            topX++;
        }
    }

    private void evaluateMethodName(String method, List<String> filtered) {
        String methodName = method.replaceAll("\\(.*\\)", "");
        List<String> methodNames = filtered.stream()
                .map(s -> s.replaceAll("\\(.*\\)", ""))
                .collect(Collectors.toList());

        if (methodName.equals(methodNames.get(0))) {
            nameTop1++;
        }

        if (methodNames.contains(methodName)) {
            nameTopX++;
        }
    }

    private String getMethod(String identifier) {
        List<String> methodNames = StringUtils.getMatches(identifier, METHOD_PATTERN);

        return methodNames.isEmpty() ? identifier : methodNames.get(0)
                .replace(" ", "");
    }

    public String getResult() {
        return "Total Events: " + totalEvents + "\n" +
                "Evaluated Events: " + evaluableEvents + "\n" +
                "Top 1 proposals: " + top1 + "\n" +
                "Top " + MAX_PROPOSALS + " proposals: " + topX + "\n" +
                "Top 1 proposals (Name only): " + nameTop1 + "\n" +
                "Top " + MAX_PROPOSALS + " proposals: (Name only)" + nameTopX + "\n";
    }
}
