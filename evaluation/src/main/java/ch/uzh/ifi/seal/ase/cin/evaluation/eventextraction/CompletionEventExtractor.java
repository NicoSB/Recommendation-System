package ch.uzh.ifi.seal.ase.cin.evaluation.eventextraction;

import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.utils.io.IReadingArchive;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

public abstract class CompletionEventExtractor {

    private static final Logger logger = LogManager.getLogger(CompletionEventExtractor.class);

    public static void extractAndConsumeCompletionEvents(IReadingArchive archive, Consumer<IIDEEvent> consumer) {
        while (archive.hasNext()) {
            try {
                IIDEEvent event = archive.getNext(IIDEEvent.class);
                if (event instanceof ICompletionEvent) {
                    consumer.accept(event);
                }
            } catch(Exception e) {
                logger.warn("Failed to deserialize an event: {}", e.getMessage());
            }
        }
    }
}
