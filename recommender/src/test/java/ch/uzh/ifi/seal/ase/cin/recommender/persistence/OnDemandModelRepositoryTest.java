package ch.uzh.ifi.seal.ase.cin.recommender.persistence;

import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OnDemandModelRepositoryTest {
    private OnDemandModelRepository _sut;
    private Path directory;

    @Before
    public void setUp() throws IOException {
        String workingDirectory = Files.createTempDirectory("TEMP").toString();
        String uniqueFolder = String.valueOf(System.currentTimeMillis());
        directory = Paths.get(workingDirectory, uniqueFolder);
        Files.createDirectory(directory);

        _sut = new OnDemandModelRepository(directory);
    }

    @After
    public void cleanUp() throws IOException {
        File directoryFile = new File(directory.toUri());
        FileUtils.deleteDirectory(directoryFile);
    }

    @Test
    public void WhenTypeModelIsAdded_RepositoryContainsTypeModel() {
        TypeModel expected = new TypeModel("Type");
        _sut.save(expected);

        TypeModel actual = _sut.find(expected.getType());

        assertEquals(expected, actual);
    }

    @Test
    public void WhenTypeModelIsAdded_ReturnsTypeNameAsId() {
        String expected = "Type";
        TypeModel model = new TypeModel(expected);

        String actual = _sut.save(model);

        assertEquals(expected, actual);
    }

    @Test
    public void WhenModelWithSameNameIsAlreadyStored_OverwritesModel() {
        TypeModel model = new TypeModel("Type");
        TypeModel expected = new TypeModel("Type");
        expected.insertPairOrIncreaseFrequency(new QuerySelectionPair(new Query(model.getType()), model.getType()));

        _sut.save(model);
        _sut.save(expected);

        TypeModel actual = _sut.find(expected.getType());

        assertEquals(expected, actual);
    }

    @Test
    public void WhenModelIsStored_FileIsCreated() {
        String typeName = "Type";
        String fileName = typeName + ".json";
        Path filePath = Paths.get(directory.toString(), fileName);
        TypeModel model = new TypeModel(typeName);

        _sut.save(model);

        assertTrue(Files.exists(filePath));
    }
}