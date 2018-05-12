package ch.uzh.ifi.seal.ase.cin.recommender;

import cc.kave.commons.utils.io.Directory;

import java.util.Set;

public class FileUtils {

    /*
     * will recursively search for all .zip files in the "dir". The paths that are
     * returned are relative to "dir".
     */
    public static Set<String> findAllZips(String dir) {

        return new Directory(dir).findFiles(s -> s.endsWith(".zip"));
    }
}
