package twitter;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Create a sample list of MockTweets
        List<SocialNetwork.MockTweet> tweets = Arrays.asList(
            new SocialNetwork.MockTweet(1, "ernie", "@bert how are you?", new Date()),
            new SocialNetwork.MockTweet(2, "ernie", "@grover let's meet", new Date()),
            new SocialNetwork.MockTweet(3, "bert", "@ernie I'm good!", new Date()),
            new SocialNetwork.MockTweet(4, "bert", "@grover join us!", new Date()),
            new SocialNetwork.MockTweet(5, "grover", "@ernie and @bert, see you soon!", new Date()),
            new SocialNetwork.MockTweet(6, "bert", "@grover good idea!", new Date()),
            new SocialNetwork.MockTweet(7, "grover", "Hello @ernie and @bert!", new Date()),
            new SocialNetwork.MockTweet(8, "ernie", "@grover thanks for coming!", new Date())
        );

        // Build the follows graph from the tweets
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        // Find the influencers
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        // Print the top 10 most-followed users
        System.out.println("Influencers (sorted by follower count):");
        for (int i = 0; i < Math.min(10, influencers.size()); i++) {
            System.out.println(influencers.get(i));
        }
    }
}