package ch.uzh.ifi.seal.ase.cin.recommender.persistence.utils;

import cc.kave.commons.model.naming.IName;
import cc.kave.commons.model.naming.impl.v0.types.ArrayTypeName;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.QuerySelectionPair;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.TypeModel;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class JsonUtilitiesTest {

    private Path directory;

    @Before
    public void setUp() throws IOException {
        String workingDirectory = Files.createTempDirectory("TEMP").toString();
        String uniqueFolder = String.valueOf(System.currentTimeMillis());
        directory = Paths.get(workingDirectory, uniqueFolder);
        Files.createDirectory(directory);
    }

    @After
    public void CleanUp() throws IOException {
        File directoryFile = new File(directory.toUri());
        FileUtils.deleteDirectory(directoryFile);
    }

    @Test
    public void CreatesJsonFileBasedOnTypeName() throws IOException {
        TypeModel model = new TypeModel("Test");
        String expectedFileName = model.getType() + ".json";

        JsonUtilities.store(directory.toString(), model);
        Path filePath = Paths.get(directory.toString(), expectedFileName);

        assertTrue(Files.exists(filePath));
    }

    @Test
    public void CorrectlyDeserializesFile() throws IOException {
        TypeModel expected = new TypeModel("Test");
        String expectedFileName = expected.getType() + ".json";

        IName selection = createSerializableName();

        Query query = new Query("Type");
        QuerySelectionPair pair = new QuerySelectionPair(query, selection);

        Path filePath = Paths.get(directory.toString(), expectedFileName);

        expected.insertPairOrIncreaseFrequency(pair);
        JsonUtilities.store(directory.toString(), expected);

        TypeModel deserialized = JsonUtilities.load(filePath.toString());

        assertEquals(expected, deserialized);
    }

    @Test
    public void WhenFileExists_FileIsOverwritten() throws IOException {
        TypeModel typeModel = new TypeModel("Test");
        String expectedFileName = typeModel.getType() + ".json";
        IName selection = createSerializableName();
        Path filePath = Paths.get(directory.toString(), expectedFileName);

        Query query = new Query("Type");
        QuerySelectionPair pair = new QuerySelectionPair(query, selection);
        typeModel.insertPairOrIncreaseFrequency(pair);
        JsonUtilities.store(directory.toString(), typeModel);

        TypeModel expected = new TypeModel("Test");
        Query query2 = new Query("Type2");
        QuerySelectionPair pair2 = new QuerySelectionPair(query2, selection);
        typeModel.insertPairOrIncreaseFrequency(pair2);
        JsonUtilities.store(directory.toString(), expected);

        TypeModel deserialized = JsonUtilities.load(filePath.toString());

        assertEquals(expected, deserialized);
    }

    private IName createSerializableName() {
        return new ArrayTypeName("id");
    }
}