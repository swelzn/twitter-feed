package com.zeromargin.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Twitter user.
 */

public class TwitterUser implements Comparable {
    private final String userId;
    private final List<String> tweets;
    private final List<TwitterUser> followers;

    public TwitterUser(String userId) {
        this.userId = userId;
        tweets = new ArrayList<>();
        followers = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getTweets() {
        return tweets;
    }

    public List<TwitterUser> getFollowers() {
        return followers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwitterUser that = (TwitterUser) o;

        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        TwitterUser user2 = (TwitterUser) o;
        return this.getUserId().compareTo(user2.getUserId());
    }

    @Override
    public String toString() {
        return "TwitterUser{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
