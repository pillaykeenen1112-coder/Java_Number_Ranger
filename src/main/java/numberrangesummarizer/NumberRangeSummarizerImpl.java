package numberrangesummarizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NumberRangeSummarizerImpl implements NumberRangeSummarizer {

    @Override
    public Collection<Integer> collect(String input) {
        // Handle null or empty input
        if (input == null || input.isEmpty()) {
            return new ArrayList<>();
        }

        // Use Java 8 Streams to process the string.
        return Arrays.stream(input.split(",")) // Split by comma
                .map(String::trim)                 // Remove whitespace
                .filter(s -> !s.isEmpty())         // Filter out empty strings
                .map(s -> {                        // Parse to Integer, handling errors
                    try {
                        return Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull) // Filter out any that failed parsing
                .collect(Collectors.toList());
    }

    @Override
    public String summarizeCollection(Collection<Integer> input) {
        // Handle null or empty input
        if (input == null || input.isEmpty()) {
            return "";
        }

        // Use Java 8 Streams to sort and de-duplicate the collection.
        List<Integer> sortedUniqueNumbers = input.stream()
                .filter(Objects::nonNull)
                .sorted()
                .distinct()
                .collect(Collectors.toList());

        List<String> resultParts = new ArrayList<>();

        for (int i = 0; i < sortedUniqueNumbers.size(); i++) {
            int rangeStart = sortedUniqueNumbers.get(i);
            int rangeEnd = rangeStart;

            int j = i;
            while (j < sortedUniqueNumbers.size() - 1 &&
                    sortedUniqueNumbers.get(j + 1) == sortedUniqueNumbers.get(j) + 1) {
                rangeEnd = sortedUniqueNumbers.get(j + 1);
                j++;
            }

            int numbersInRange = (rangeEnd - rangeStart) + 1;

            if (numbersInRange >= 3) {
                resultParts.add(rangeStart + "-" + rangeEnd);
                i = j;
            } else {
                resultParts.add(String.valueOf(rangeStart));
            }
        }

        // Use String.join (from Java 8) and add the spaces as shown in the sample result.
        return String.join(", ", resultParts);
    }
}