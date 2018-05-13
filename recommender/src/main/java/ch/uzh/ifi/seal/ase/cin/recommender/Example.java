package ch.uzh.ifi.seal.ase.cin.recommender;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.rsse.calls.datastructures.Tuple;
import ch.uzh.ifi.seal.ase.cin.recommender.model.*;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.ModelRepository;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.OnDemandModelRepository;
import ch.uzh.ifi.seal.ase.cin.recommender.recommendation.BasicMethodRecommender;
import ch.uzh.ifi.seal.ase.cin.recommender.recommendation.MethodRecommender;
import ch.uzh.ifi.seal.ase.cin.recommender.recommendation.RecommendationSystem;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

public class Example {

    public static final String MINED_MODEL_PATH = "C:\\Users\\ncstr\\MinedModel";
    public static final String MODEL_PATH = "C:\\Users\\ncstr\\Model";

    public static void main(String[] args) {
        Query query = createQuery();
        System.out.println("Query: ");
        System.out.println(query);

        demonstrateLocalIncrementation(query);
        demonstrateMinedModel(query);
    }

    private static Query createQuery() {
        String builder = "public void Method() {\n" +
                "  string a = \"Foo\";\n" +
                "  string b = a.\n" +
                "}";
        System.out.println("Analysed code: ");
        System.out.println(builder);
        System.out.println();
        System.out.println();

        Query q = new Query("System.String");
        q.setEnclosingNodeKind(EnclosingNodeKind.DEFAULT);
        String[] types = {"System.String"};
        q.setExpectedTypes(types);
        q.setEnclosingMethodVisibility(Visibility.PUBLIC);
        q.setCompletionNodeKind(CompletionNodeKind.DEFAULT);
        q.setEnclosingMethodKind(MethodKind.INSTANCE);
        return q;
    }

    private static void demonstrateLocalIncrementation(Query query) {
        Path modelPath = Paths.get(MODEL_PATH);
        RecommendationSystem recommendationSystem = createRecommendationSystem(modelPath);

        Set<Tuple<IMethodName, Double>> results = recommendationSystem.query(query);
        System.out.println("=======Initial recommendation: ");
        printResults(results);
        System.out.println();
        System.out.println();

        System.out.print("Insert Selection: ");
        Scanner in;
        in = new Scanner(System.in);

        String s = in.nextLine();
        recommendationSystem.updateModel(query, new MethodName(s));
        results = recommendationSystem.query(query);
        System.out.println("=======New Recommendation: ");
        printResults(results);
        System.out.println();
        System.out.println();
    }

    private static void printResults(Set<Tuple<IMethodName, Double>> results) {
        results.forEach(System.out::println);
    }

    private static void demonstrateMinedModel(Query query) {
        Path modelPath = Paths.get(MINED_MODEL_PATH);
        RecommendationSystem recommendationSystem = createRecommendationSystem(modelPath);

        Set<Tuple<IMethodName, Double>> results = recommendationSystem.query(query);
        System.out.println("=======Globally trained Recommendation: ");
        printResults(results);
    }

    private static RecommendationSystem createRecommendationSystem(Path modelPath) {
        ModelRepository repository = new OnDemandModelRepository(modelPath);
        MethodRecommender recommender = new BasicMethodRecommender();

        return new RecommendationSystem(repository, recommender);
    }
}
