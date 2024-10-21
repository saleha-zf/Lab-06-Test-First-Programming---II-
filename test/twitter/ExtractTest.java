package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;

public class ExtractTest {

    /*
     * Testing strategy for getTimespan():
     * - Partition based on number of tweets:
     *     - No tweets (empty list)
     *     - One tweet
     *     - Two tweets with same timestamp
     *     - Two tweets with different timestamps
     * 
     * Testing strategy for getMentionedUsers():
     * - Partition based on mentions in tweet text:
     *     - No mentions
     *     - One mention
     *     - Multiple mentions
     *     - Mixed case mentions (e.g., @ALICE vs. @alice)
     *     - Mentions with punctuation
     *     - Multiple tweets with and without mentions
     */
    
    private static final Instant d1 = Instant.parse("2024-10-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2024-10-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "saleha_zainab_fatima_404329", "Excited for BESE-13B final project!", d1);
    private static final Tweet tweet2 = new Tweet(2, "muhammad.ahmed", "BESE-13B is killing it! #404329", d2);
    private static final Tweet tweet3 = new Tweet(3, "amna.khan", "Collaborating with @mohsin.ali and @sadia.rauf on the final project.", d1);
    private static final Tweet tweet4 = new Tweet(4, "mohsin.ali", "No mentions here, just focusing on the project.", d2);
    private static final Tweet tweet5 = new Tweet(5, "sadia.rauf", "Shoutout to @saleha_zainab_fatima_404329 for leading our group! @SALEHA_ZAINAB_FATIMA_404329", d2);

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespanNoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList());
        
        // Expect the start and end times to be null for an empty tweet list
        assertNull("expected null start for empty list", timespan.getStart());
        assertNull("expected null end for empty list", timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanOneTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanTwoTweetsSameTime() {
        Tweet tweetSameTime1 = new Tweet(6, "faiza.jamal", "Excited for final project!", d1);
        Tweet tweetSameTime2 = new Tweet(7, "hamza.hassan", "Wrapping up the final touches.", d1);
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweetSameTime1, tweetSameTime2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanTwoTweetsDifferentTime() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet4));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMention
