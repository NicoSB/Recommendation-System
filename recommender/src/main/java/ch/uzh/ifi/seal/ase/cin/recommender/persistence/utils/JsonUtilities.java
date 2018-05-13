package ch.uzh.ifi.seal.ase.cin.recommender.persistence.utils;

import cc.kave.commons.utils.io.json.JsonUtils;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.TypeModel;
import ch.uzh.ifi.seal.ase.cin.recommender.util.SSTUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonUtilities {

    private static final String JSON_POSTFIX = ".json";
    private static Logger logger = LogManager.getLogger(JsonUtilities.class);


    public static void store(String directory, TypeModel model) {
        String fileName = SSTUtils.getFullyQualifiedIdentifier(model.getType()) + JSON_POSTFIX;
        try {
            Path filePath = Paths.get(directory, fileName);
            File file = new File(filePath.toString());
            JsonUtils.toJson(model, file);
        } catch (InvalidPathException e) {
            logger.warn("Was not able to store model for type '{}'.", model.getType());
        }
    }

    public static TypeModel load(String uri) {
        return JsonUtils.fromJson(new File(uri), TypeModel.class);
    }
}
