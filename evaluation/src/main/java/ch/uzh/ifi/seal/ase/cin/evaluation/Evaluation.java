package ch.uzh.ifi.seal.ase.cin.evaluation;

import ch.uzh.ifi.seal.ase.cin.evaluation.evaluation.Evaluator;
import ch.uzh.ifi.seal.ase.cin.evaluation.eventextraction.EventFilter;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.ModelRepository;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.OnDemandModelRepository;
import ch.uzh.ifi.seal.ase.cin.recommender.recommendation.BasicMethodRecommender;
import ch.uzh.ifi.seal.ase.cin.recommender.recommendation.MethodRecommender;
import ch.uzh.ifi.seal.ase.cin.recommender.recommendation.RecommendationSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;

public class Evaluation {

    private static final String ARG_EXTRACTION = "-e";
    private static final Logger logger = LogManager.getLogger(Evaluation.class);

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Not enough arguments provided! Syntax: inputDirectory");
            return;
        }

        String inputDirectory = args[0];

        if (args.length == 3 && ARG_EXTRACTION.equals(args[1])) {
            extractCompletionEvents(inputDirectory, args[2]);
        } else if(args.length == 2) {
            evaluateDirectory(inputDirectory, args[1]);
        }
    }

    private static void extractCompletionEvents(String inputDirectory, String outputDirectory) {
        new EventFilter(inputDirectory, outputDirectory).run();
    }

    private static void evaluateDirectory(String inputDirectory, String modelDirectory) {
        ModelRepository repository = new OnDemandModelRepository(Paths.get(modelDirectory));
        MethodRecommender recommender = new BasicMethodRecommender();
        RecommendationSystem recommendationSystem = new RecommendationSystem(repository, recommender);

        Evaluator evaluator = new Evaluator(inputDirectory, recommendationSystem);
        evaluator.run();
        logger.warn(evaluator.getResult());
    }
}
