package ch.uzh.ifi.seal.ase.cin.miner;

import ch.uzh.ifi.seal.ase.cin.miner.utils.FileUtils;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.ModelRepository;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.OnDemandModelRepository;

import java.nio.file.Paths;
import java.util.Set;

public class Miner {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Not enough arguments provided! Syntax: inputDirectory modelDirectory");
        }

        String inputDirectory = args[0];
        String modelDirectory = args[1];

        ModelRepository repository = new OnDemandModelRepository(Paths.get(modelDirectory));
        RepositoryUpdater updater = new RepositoryUpdater(repository);
        SSTProcessor sstProcessor = new SSTProcessor(updater);

        processZips(inputDirectory, sstProcessor);
    }

    private static void processZips(String inputDirectory, SSTProcessor sstProcessor) {
        Set<String> zips = FileUtils.findAllZips(inputDirectory);
        ZipProcessor processor = new ZipProcessor(sstProcessor);
        zips.forEach(zip -> processor.processZip(Paths.get(inputDirectory, zip).toString()));
    }
}
