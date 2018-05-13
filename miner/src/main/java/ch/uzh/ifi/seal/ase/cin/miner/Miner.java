package ch.uzh.ifi.seal.ase.cin.miner;

import ch.uzh.ifi.seal.ase.cin.miner.utils.FileUtils;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.ModelRepository;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.OnDemandModelRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.util.Set;

public class Miner {
    private static Logger logger = LogManager.getLogger(Miner.class);

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Not enough arguments provided! Syntax: inputDirectory modelDirectory");
        }

        String inputDirectory = args[0];
        String modelDirectory = args[1];

        InMemoryRepository repository = new InMemoryRepository(Paths.get(modelDirectory));
        RepositoryUpdater updater = new RepositoryUpdater(repository);
        SSTProcessor sstProcessor = new SSTProcessor(updater);

        processZips(inputDirectory, sstProcessor);
    }

    private static void processZips(String inputDirectory, SSTProcessor sstProcessor) {
        logger.info("Collection zips in {}", inputDirectory);

        Set<String> zips = FileUtils.findAllZips(inputDirectory);
        ZipProcessor processor = new ZipProcessor(sstProcessor);
        int totalZips = zips.size();
        int counter = 1;

        for (String zip : zips) {
            logger.info("Processing zip {}: [{}/{}]", zip, counter, totalZips);
            processor.processZip(Paths.get(inputDirectory, zip).toString());
            counter++;
        }
    }
}
