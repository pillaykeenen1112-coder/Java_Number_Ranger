//  KEENEN PILLAY
//  pillaykeenen1112@gmail.com
package numberrangesummarizer;
import java.util.*;
import java.util.stream.Collectors; // using 'stream' which is function in Java 8

public class NumberRangeSummarizerImpl implements NumberRangeSummarizer {

    public Collection<Integer> collect(String input) {
        // Handles null or empty input in a list
        if (input == null || input.isEmpty()) {
            return Collections.emptyList();
        }

        // Uses Java 8 Streams to process the string.
        return Arrays.stream(input.split(",")) // Split by comma
                .map(String::trim)                 // Remove whitespace
                .filter(s -> !s.isEmpty())         // Filter out empty strings
                .map(s -> {                        // Parse to Integer, handling errors
                    try {
                        return Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                        return null; // ignore invalid numbers by returning null
                    }
                })
                .filter(Objects::nonNull) // Filter out any that failed parsing
                .collect(Collectors.toList());  // standard structure of stream chaining
    }
    // Summarizes a collection of integers into compact ranges
    public String summarizeCollection(Collection<Integer> input) {
        // Handle null or empty input
        if (input == null || input.isEmpty()) {
            return "";
        }

        // Use Java 8 Streams to sort and de-duplicate the collection.
        List<Integer> sortedUniqueNumbers = input.stream()
                .filter(Objects::nonNull)   //removes any nulls in collection
                .sorted()                   // sort the numbers
                .distinct()                 // remove duplicates if there are any
                .collect(Collectors.toList());

        List<String> ranges = new ArrayList<>(); // list to store finalised summarized numbers
        // iterate through sorted numbers to detect consecutive ranges
        for (int i = 0; i < sortedUniqueNumbers.size(); i++) {
            int rangeStart = sortedUniqueNumbers.get(i); // start range
            int rangeEnd = rangeStart;  // end potential range

            int j = i;
            // Keep looping while the next number is consecutive (+1 difference)
            while (j < sortedUniqueNumbers.size() - 1 &&
                    sortedUniqueNumbers.get(j + 1) == sortedUniqueNumbers.get(j) + 1) {
                rangeEnd = sortedUniqueNumbers.get(j + 1); // extend range
                j++; // go to the next number
            }
            // Calculate how many numbers are in the range
            int numbersInRange = (rangeEnd - rangeStart) + 1;
            // If the range has 3 or more numbers, use "start-end" format (e.g., 1-3)
            // Otherwise, just show the single number
            if (numbersInRange >= 3) {
                ranges.add(rangeStart + "-" + rangeEnd);
                i = j; // skip over the processed range
            } else {
                ranges.add(String.valueOf(rangeStart));
            }
        }

        // Use String.join from Java 8 and add the spaces as shown in the sample result.
        return String.join(", ", ranges);
    }
}