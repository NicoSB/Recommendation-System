package ch.uzh.ifi.seal.ase.cin.miner.utils;


import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class FileUtilsTest {

    @Test
    public void recognizesAllZipFiles() {
        String respath = "src/test/resources/FileUtilsTest";
        Set<String> zips = FileUtils.findAllZips(respath);

        assertEquals(2, zips.size());
        assertTrue(zips.contains("test1.zip"));
        assertTrue(zips.contains("test2.zip"));
    }

    @Test
    public void skipsNonZipFiles() {
        String respath = "src/test/resources/FileUtilsTest";
        Set<String> zips = FileUtils.findAllZips(respath);

        assertFalse(zips.contains("test3.txt"));
    }
}
