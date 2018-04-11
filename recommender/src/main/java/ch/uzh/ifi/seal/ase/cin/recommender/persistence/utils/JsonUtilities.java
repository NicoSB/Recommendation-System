package ch.uzh.ifi.seal.ase.cin.recommender.persistence.utils;

import cc.kave.commons.utils.io.json.JsonUtils;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.TypeModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

class JsonUtilities {

    private static final String JSON_POSTFIX = ".json";

    public static void store(String directory, TypeModel model) throws IOException {
        String json = JsonUtils.toJson(model);
        String fileName = model.getType() + JSON_POSTFIX;

        Path filePath = Paths.get(directory, fileName);

        Files.write(filePath, json.getBytes());
    }

    public static TypeModel load(String uri) {
        File file = new File(uri);
        return JsonUtils.fromJson(file, TypeModel.class);
    }
}
