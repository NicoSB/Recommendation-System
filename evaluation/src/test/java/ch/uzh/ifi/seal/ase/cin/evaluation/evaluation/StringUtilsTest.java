package ch.uzh.ifi.seal.ase.cin.evaluation.evaluation;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void extractsRegexFromString() {
        String expected = "WriteLine";
        String testString = ",-" + expected + "-.,";
        String regex = "\\w+";

        List<String> results = StringUtils.getMatches(testString, regex);

        assertEquals(1, results.size());
        assertEquals(expected, results.get(0));
    }

    @Test
    public void extractsMultipleMatches() {
        String expected = "WriteLine";
        String testString = ",-" + expected + "-.," + expected;
        String regex = "\\w+";

        List<String> results = StringUtils.getMatches(testString, regex);

        assertEquals(2, results.size());
        assertEquals(expected, results.get(0));
        assertEquals(expected, results.get(1));
    }

    @Test
    public void returnsEmptyWhenNoMatchesArePresent() {
        String testString = ",--.,";
        String regex = "\\w+";

        List<String> results = StringUtils.getMatches(testString, regex);

        assertEquals(0, results.size());
    }


}