package ch.uzh.ifi.seal.ase.cin.recommender.persistence.utils;

import cc.kave.commons.utils.io.json.JsonUtils;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.TypeModel;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonUtilities {

    private static final String JSON_POSTFIX = ".json";


    public static void store(String directory, TypeModel model) {
        String fileName = model.getType() + JSON_POSTFIX;

        Path filePath = Paths.get(directory, fileName);
        File file = new File(filePath.toString());
        JsonUtils.toJson(model, file);
    }

    public static TypeModel load(String uri) {
        return JsonUtils.fromJson(new File(uri), TypeModel.class);
    }
}
