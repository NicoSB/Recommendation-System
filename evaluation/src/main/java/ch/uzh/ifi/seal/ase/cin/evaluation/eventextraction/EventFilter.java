package ch.uzh.ifi.seal.ase.cin.evaluation.eventextraction;

import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.utils.io.IReadingArchive;
import cc.kave.commons.utils.io.IWritingArchive;
import cc.kave.commons.utils.io.ReadingArchive;
import cc.kave.commons.utils.io.WritingArchive;
import ch.uzh.ifi.seal.ase.cin.miner.utils.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Paths;
import java.util.Set;

public class EventFilter {
    private final Logger logger = LogManager.getLogger(EventFilter.class);

    private final int MAX_EVENTS_PER_FILE = 5000;

    private String inputDirectory;
    private String outputDirectory;

    private IWritingArchive writingArchive;
    private int counter = 1;
    private int total = 0;
    private int fileCounter = 0;
    private int eventCounter = 0;

    public EventFilter(String inputDirectory, String outputDirectory) {
        this.outputDirectory = outputDirectory;
        this.inputDirectory = inputDirectory;
    }

    public void run() {
        logger.warn("Starting filter with input: '{}' and output: '{}'", inputDirectory, outputDirectory);
        extractAndStoreCompletionEventsFromDirectory();
    }

    private void extractAndStoreCompletionEventsFromDirectory() {
        Set<String> zips = FileUtils.findAllZips(inputDirectory);

        total = zips.size();
        for (String zip : zips) {
            extractAndStoreCompletionEventsFromZip(Paths.get(inputDirectory, zip).toString());
        }
    }

    private void extractAndStoreCompletionEventsFromZip(String zip) {
        System.out.printf("Extracting Zip %d/%d\n", counter++, total);
        IReadingArchive readingArchive = new ReadingArchive(new File(zip));
        createNextWritingArchive();

        CompletionEventExtractor.extractAndConsumeCompletionEvents(readingArchive, this::addEvent);
    }

    private void addEvent(IIDEEvent iideEvent) {
        writingArchive.add(iideEvent);
        eventCounter++;
        if (eventCounter % MAX_EVENTS_PER_FILE == 0) {
            createNextWritingArchive();
        }
    }

    private void createNextWritingArchive() {
        if (writingArchive != null) {
            writingArchive.close();
        }

        String zipName = fileCounter++ + ".zip";
        String filePath = Paths.get(outputDirectory, zipName).toString();
        writingArchive = new WritingArchive(new File(filePath));
    }
}
