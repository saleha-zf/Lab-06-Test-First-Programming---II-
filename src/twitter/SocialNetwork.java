package twitter;

import java.util.*;

public class SocialNetwork {

    // Tweet class definition
    public static class Tweet {
        private int id;
        private String author;
        private String content;
        private Date date;

        public Tweet(int id, String author, String content, Date date) {
            this.id = id;
            this.author = author;
            this.content = content;
            this.date = date;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public int getId() {
            return id;
        }

        public Date getDate() {
            return date;
        }
    }

    public static class Extract {
        // Method to extract mentioned users from Tweet objects
        public static Set<String> getMentionedUsers(List<Tweet> tweets) {
            Set<String> mentionedUsers = new HashSet<>();
            for (Tweet tweet : tweets) {
                String content = tweet.getContent();
                String[] words = content.split("\\s+");
                for (String word : words) {
                    if (word.startsWith("@")) {
                        mentionedUsers.add(word.substring(1).toLowerCase());
                    }
                }
            }
            return mentionedUsers;
        }

        // Renamed method for MockTweet
        public static Set<String> getMentionedUsersFromMock(List<MockTweet> tweets) {
            Set<String> mentionedUsers = new HashSet<>();
            for (MockTweet tweet : tweets) {
                String content = tweet.getContent();
                String[] words = content.split("\\s+");
                for (String word : words) {
                    if (word.startsWith("@")) {
                        mentionedUsers.add(word.substring(1).toLowerCase());
                    }
                }
            }
            return mentionedUsers;
        }
    }

    // MockTweet class for testing
    public static class MockTweet {
        private int id;
        private String author;
        private String content;
        private Date date;

        public MockTweet(int id, String author, String content, Date date) {
            this.id = id;
            this.author = author;
            this.content = content;
            this.date = date;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public int getId() {
            return id;
        }

        public Date getDate() {
            return date;
        }
    }

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets a list of tweets providing the evidence, not modified by this
     * method.
     * @return a social network (as defined above) in which users follow each other
     * based on @-mentions in the tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<MockTweet> tweets) {
        Map<String, Set<String>> followsGraph = new HashMap<>();

        // Convert MockTweets to Tweets
        List<Tweet> tweetList = new ArrayList<>();
        for (MockTweet mockTweet : tweets) {
            tweetList.add(new Tweet(mockTweet.getId(), mockTweet.getAuthor(), mockTweet.getContent(), mockTweet.getDate()));
        }

        for (Tweet tweet : tweetList) {
            String author = tweet.getAuthor().toLowerCase();
            Set<String> mentions = Extract.getMentionedUsers(tweetList); // Get mentions from the Tweet list

            // Add the author to the map if not already present
            followsGraph.putIfAbsent(author, new HashSet<>());

            // Add mentions as users that the author follows
            for (String mention : mentions) {
                if (!mention.equalsIgnoreCase(author)) {
                    followsGraph.get(author).add(mention.toLowerCase());
                }
            }
        }

        return followsGraph;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     * descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        Map<String, Integer> followerCounts = new HashMap<>();

        // Count the number of followers each person has
        for (Set<String> followers : followsGraph.values()) {
            for (String followed : followers) {
                followerCounts.put(followed, followerCounts.getOrDefault(followed, 0) + 1);
            }
        }

        // Sort the users by follower count
        List<String> sortedInfluencers = new ArrayList<>(followerCounts.keySet());
        sortedInfluencers.sort((a, b) -> followerCounts.get(b) - followerCounts.get(a));

        return sortedInfluencers;
    }
}