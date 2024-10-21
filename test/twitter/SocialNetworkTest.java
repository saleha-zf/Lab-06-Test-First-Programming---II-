package twitter;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.Test;

public class SocialNetworkTest {

 

    // guessFollowsGraph() tests

    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphNoMentions() {
        List<SocialNetwork.MockTweet> tweets = Arrays.asList(
            new SocialNetwork.MockTweet(1, "ernie", "Hello world!", new Date())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        assertTrue("expected empty set of follows", followsGraph.get("ernie").isEmpty());
    }

    @Test
    public void testGuessFollowsGraphSingleMention() {
        List<SocialNetwork.MockTweet> tweets = Arrays.asList(
            new SocialNetwork.MockTweet(1, "ernie", "@bert how are you?", new Date())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        assertTrue("expected ernie to follow bert", followsGraph.get("ernie").contains("bert"));
    }

    @Test
    public void testGuessFollowsGraphMultipleMentions() {
        List<SocialNetwork.MockTweet> tweets = Arrays.asList(
            new SocialNetwork.MockTweet(1, "ernie", "@bert and @grover let's meet", new Date())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        assertTrue("expected ernie to follow bert", followsGraph.get("ernie").contains("bert"));
        assertTrue("expected ernie to follow grover", followsGraph.get("ernie").contains("grover"));
    }

    @Test
    public void testGuessFollowsGraphMultipleTweetsSameUser() {
        List<SocialNetwork.MockTweet> tweets = Arrays.asList(
            new SocialNetwork.MockTweet(1, "ernie", "@bert good morning", new Date()),
            new SocialNetwork.MockTweet(2, "ernie", "@grover good night", new Date())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        assertTrue("expected ernie to follow bert", followsGraph.get("ernie").contains("bert"));
        assertTrue("expected ernie to follow grover", followsGraph.get("ernie").contains("grover"));
    }

    // influencers() tests

    @Test
    public void testInfluencersEmptyGraph() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue("expected empty list of influencers", influencers.isEmpty());
    }

    @Test
    public void testInfluencersSingleUserNoFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("ernie", new HashSet<>());
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue("expected empty list of influencers", influencers.isEmpty());
    }

    @Test
    public void testInfluencersSingleInfluencer() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("ernie", new HashSet<>(Arrays.asList("bert")));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("expected bert to be the only influencer", Arrays.asList("bert"), influencers);
    }

    @Test
    public void testInfluencersMultipleInfluencers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("ernie", new HashSet<>(Arrays.asList("bert", "grover")));
        followsGraph.put("bert", new HashSet<>(Arrays.asList("grover")));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("expected grover to be the top influencer", "grover", influencers.get(0));
        assertEquals("expected bert to be the second influencer", "bert", influencers.get(1));
    }

    @Test
    public void testInfluencersTiedInfluence() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("ernie", new HashSet<>(Arrays.asList("bert")));
        followsGraph.put("grover", new HashSet<>(Arrays.asList("bert")));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("expected bert to be the top influencer", Arrays.asList("bert"), influencers);
    }
}