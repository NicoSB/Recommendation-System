package ch.uzh.ifi.seal.ase.cin.evaluation.evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static List<String> getMatches(String testString, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(testString);

        List<String> matches = new ArrayList<>();
        while(matcher.find()) {
            matches.add(matcher.group());
        }

        return matches;
    }
}
