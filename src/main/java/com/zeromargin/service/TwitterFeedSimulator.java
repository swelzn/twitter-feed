package com.zeromargin.service;

import com.zeromargin.model.TwitterUser;
import com.zeromargin.util.ReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * This is a twitter-like feed simulator.
 * It takes a file containing users and a file containing tweets and outputs their content as a twitter-like feed.
 */
@Service
public class TwitterFeedSimulator {
    private static final Logger logger = LoggerFactory.getLogger(TwitterFeedSimulator.class);

    /**
     * Gets a list of users from the given file.
     *
     * @param fileName the name of the file to be read
     * @return a list of users
     */
    public Set<TwitterUser> getUsers(String fileName) {
        if (fileName == null) {
            logger.warn("File name is null.");
            return null;
        }

        Set<TwitterUser> userList = new TreeSet<>();
        try {
            Map<String, Set<String>> map = new TreeMap<>();
            String text;
            try {
                text = ReaderUtil.readFile(fileName);
            } catch (Exception e) {
                logger.warn("File " + fileName + " could not be read.");
                return null;
            }

            for (String line : text.split("\n")) {
                if (!line.contains("follows")) {
                    // invalid input data
                    logger.warn("Invalid line: " + line);
                    return null;
                }

                String[] users = line.split("follows"); // [Ward], [Alan,Martin]
                String follower = users[0].trim();
                for (String user : users[1].split(",")) {
                    if (map.containsKey(user.trim())) {
                        map.get(user.trim()).add(follower);
                    } else {
                        Set<String> followers = new TreeSet<>();
                        followers.add(follower);
                        map.put(user.trim(), followers);
                    }
                }

                map.putIfAbsent(follower, new TreeSet<>());
            }

            map.keySet().forEach(user -> {
                TwitterUser twitterUser = new TwitterUser(user);
                map.get(user).forEach(follower -> twitterUser.getFollowers().add(new TwitterUser(follower)));
                userList.add(twitterUser);
            });
        } catch (Exception e) {
            logger.error("Could not read user file.", e);
            return null;
        }
        return userList;
    }

    /**
     * Gets a list of tweets from the given file.
     *
     * @param fileName the name of the file to be read
     * @return a list of tweets.
     */
    public Map<String, List<String>> getTweets(String fileName) {
        if (fileName == null) {
            logger.warn("File name is null.");
            return null;
        }

        Map<String, List<String>> map = new HashMap<>();
        try {
            String text = ReaderUtil.readFile(fileName);
            for (String line : text.split("\n")) {
                if (!line.contains(">")) {
                    // invalid tweet, no need for further processing
                    logger.warn("Invalid tweet: " + line);
                    return null;
                }
                // split the text
                String[] tokens = line.split(">");
                String userId = tokens[0].trim();
                String tweet = tokens[1].trim();

                // check if the user has previously been added to the map
                if (map.containsKey(userId)) {
                    // add to the user's tweets
                    map.get(userId).add(tweet);
                } else {
                    ArrayList<String> tweets = new ArrayList<>();
                    tweets.add(tweet);
                    // add the user and the tweet
                    map.put(userId, tweets);
                }
            }
        } catch (Exception e) {
            logger.error("Could not read tweets file.", e);
            return null;
        }
        return map;
    }

    /**
     * Performs the simulation.
     */
    public void simulate(String userFileName, String tweetFileName) {
        Set<TwitterUser> users = getUsers(userFileName);
        if (users == null) {
            logger.warn("No users found. Terminating simulation.");
            return;
        }

        Map<String, List<String>> tweets = getTweets(tweetFileName);
        if (tweets == null) {
            logger.warn("No tweets found. Terminating simulation.");
            return;
        }

        logger.info("Setting user tweets...");
        setUserTweets(users, tweets);
        logger.info("User tweets set. Simulating...");

        users.forEach(user -> {
            logger.info(user.getUserId());
            user.getTweets().stream().map(tweet -> "@" + user.getUserId() + ": " + tweet).forEach(logger::info);
        });
    }

    /**
     * Populates all user tweets.
     *
     * @param users  the list of users
     * @param tweets the tweets
     */
    private void setUserTweets(Set<TwitterUser> users, Map<String, List<String>> tweets) {
        for (TwitterUser user : users) {
            // does this user have any tweets?
            if (tweets.containsKey(user.getUserId())) {
                user.getTweets().addAll(tweets.get(user.getUserId()));
            }
        }
    }
}
