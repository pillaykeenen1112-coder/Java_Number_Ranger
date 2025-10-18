package numberrangesummarizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberRangeSummarizerImplTest {

    private NumberRangeSummarizer summarizer;

    @BeforeEach
    void setUp() {
        summarizer = new NumberRangeSummarizerImpl();
    }

    @Test
    @DisplayName("Test collect() with a standard string")
    void testCollectBasic() {
        Collection<Integer> result = summarizer.collect("1,3,6,7,8");
        assertEquals(5, result.size());
        assertTrue(result.containsAll(Arrays.asList(1, 3, 6, 7, 8)));
    }

    @Test
    @DisplayName("Test collect() with whitespace")
    void testCollectWithWhitespace() {
        Collection<Integer> result = summarizer.collect("1, 3, 6, 7, 8");
        assertEquals(5, result.size());
        assertTrue(result.containsAll(Arrays.asList(1, 3, 6, 7, 8)));
    }

    @Test
    @DisplayName("Test collect() robustness with invalid characters")
    void testCollectWithInvalidChars() {
        Collection<Integer> result = summarizer.collect("1,a,3,b,5,!");
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList(1, 3, 5)));
    }

    @Test
    @DisplayName("Test collect() with null input")
    void testCollectNull() {
        Collection<Integer> result = summarizer.collect(null);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Test summarizeCollection() with the exact sample from the email")
    void testSummarizeWithExactSample() {
        Collection<Integer> input = Arrays.asList(1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31);
        String expected = "1, 3, 6-8, 12-15, 21-24, 31";
        assertEquals(expected, summarizer.summarizeCollection(input));
    }

    @Test
    @DisplayName("Test assumption: two sequential numbers are not a range")
    void testSummarizeTwoSequentialNumbers() {
        Collection<Integer> input = Arrays.asList(1, 2, 4, 5, 7, 8, 9);
        String expected = "1, 2, 4, 5, 7-9";
        assertEquals(expected, summarizer.summarizeCollection(input));
    }

    @Test
    @DisplayName("Test summarizeCollection() with unsorted and duplicate input")
    void testSummarizeUnsortedAndDuplicates() {
        Collection<Integer> input = Arrays.asList(5, 1, 3, 2, 4, 8, 10, 9, 8, 3);
        String expected = "1-5, 8-10";
        assertEquals(expected, summarizer.summarizeCollection(input));
    }

    @Test
    @DisplayName("Test the full process with the sample input string")
    void testFullProcessWithSample() {
        // The sample input string in the email was "1,3,6,7,8,12,13,14,15,21,22,23,24,31"
        // Note: It's missing a comma, which my robust parser handles.
        // Let's assume the string was a typo and should have had a comma.
        String inputString = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        String expected = "1, 3, 6-8, 12-15, 21-24, 31";

        Collection<Integer> collected = summarizer.collect(inputString);
        String result = summarizer.summarizeCollection(collected);

        assertEquals(expected, result);
    }
}