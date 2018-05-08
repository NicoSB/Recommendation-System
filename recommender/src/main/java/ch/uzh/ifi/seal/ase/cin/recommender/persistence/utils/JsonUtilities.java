package ch.uzh.ifi.seal.ase.cin.recommender.persistence.utils;

import ch.uzh.ifi.seal.ase.cin.recommender.persistence.TypeModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonUtilities {

    private static final String JSON_POSTFIX = ".json";
    private static Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithModifiers(Modifier.STATIC).setPrettyPrinting();
        gson = builder.create();
    }

    public static void store(String directory, TypeModel model) throws IOException {
        String json = gson.toJson(model);
        String fileName = model.getType() + JSON_POSTFIX;

        Path filePath = Paths.get(directory, fileName);

        Files.write(filePath, json.getBytes());
    }

    public static TypeModel load(String uri) {
        try {
            JsonReader reader = new JsonReader(new FileReader(uri));
            return gson.fromJson(reader, TypeModel.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }
}
