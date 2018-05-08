package ch.uzh.ifi.seal.ase.cin.miner;

import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.utils.io.IReadingArchive;
import cc.kave.commons.utils.io.ReadingArchive;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;

public class ZipProcessor {
    private SSTProcessor processor;

    public ZipProcessor(SSTProcessor processor) {
        this.processor = processor;
    }

    public void processZip(String zipUri) {
        IReadingArchive readingArchive = new ReadingArchive(new File(zipUri));

        while (readingArchive.hasNext()) {
            Context context = readingArchive.getNext(Context.class);
            processor.processSST(context.getSST());
        }
    }
}
