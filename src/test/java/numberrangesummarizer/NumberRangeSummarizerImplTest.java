package numberrangesummarizer;
import java.util.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberRangeSummarizerImplTest {

    private NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();

    @Test
    void testCollectBasic() {
        Collection<Integer> result = summarizer.collect("1,3,6,7,8");
        assertEquals(5, result.size());
        assertTrue(result.containsAll(Arrays.asList(1, 3, 6, 7, 8)));
    }

    @Test
    void testCollectWithWhitespace() {
        Collection<Integer> result = summarizer.collect("1, 3, 6, 7, 8");
        assertEquals(5, result.size());
        assertTrue(result.containsAll(Arrays.asList(1, 3, 6, 7, 8)));
    }

    @Test
    void testCollectWithInvalidChars() {
        Collection<Integer> result = summarizer.collect("1,a,3,b,5,!");
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList(1, 3, 5)));
    }

    @Test
    void testCollectNull() {
        Collection<Integer> result = summarizer.collect(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSummarizeWithExactSample() {
        Collection<Integer> input = Arrays.asList(1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31);
        String expected = "1, 3, 6-8, 12-15, 21-24, 31";
        assertEquals(expected, summarizer.summarizeCollection(input));
    }

    @Test
    void testSummarizeTwoSequentialNumbers() {
        Collection<Integer> input = Arrays.asList(1, 2, 4, 5, 7, 8, 9);
        String expected = "1, 2, 4, 5, 7-9";
        assertEquals(expected, summarizer.summarizeCollection(input));
    }

    @Test
    void testSummarizeUnsortedAndDuplicates() {
        Collection<Integer> input = Arrays.asList(5, 1, 3, 2, 4, 8, 10, 9, 8, 3);
        String expected = "1-5, 8-10";
        assertEquals(expected, summarizer.summarizeCollection(input));
    }

    @Test
    void testFullProcessWithSample() {
        // The sample input string in the email was "1,3,6,7,8,12,13,14,15,21,22,23,24,31"

        String inputString = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        String expected = "1, 3, 6-8, 12-15, 21-24, 31";

        Collection<Integer> collected = summarizer.collect(inputString);
        String result = summarizer.summarizeCollection(collected);
        assertEquals(expected, result);
        // Just print the final results using the sample input in final test
        System.out.println("THE INPUT IS: " + inputString);
        System.out.println("THE OUTPUT IS: " + result);
    }
}