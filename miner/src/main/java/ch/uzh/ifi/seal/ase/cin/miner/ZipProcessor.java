package ch.uzh.ifi.seal.ase.cin.miner;

import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.utils.io.IReadingArchive;
import cc.kave.commons.utils.io.ReadingArchive;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ZipProcessor {
    private SSTProcessor processor;
    private Logger logger = LogManager.getLogger(ZipProcessor.class);

    public ZipProcessor(SSTProcessor processor) {
        this.processor = processor;
    }

    public void processZip(String zipUri) {
        IReadingArchive readingArchive = new ReadingArchive(new File(zipUri));
        int total = readingArchive.getNumberOfEntries();
        int counter = 1;

        while (readingArchive.hasNext()) {
            Context context = readingArchive.getNext(Context.class);
            logger.info("Processing context [{}/{}]", counter++, total);
            processor.processSST(context.getSST());
        }

        processor.store();
    }
}
