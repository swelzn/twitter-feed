package com.zeromargin;

import com.zeromargin.service.TwitterFeedSimulator;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwitterFeedApplicationTests {

    @Autowired
    TwitterFeedSimulator twitterFeedSimulator;

    @Test
    public void testSimulate() {
        twitterFeedSimulator.simulate("user.txt", "tweet.txt");
    }

    @Test
    public void testFail() {
        Assert.assertNull(twitterFeedSimulator.getTweets("badtweets.txt"));
        Assert.assertNull(twitterFeedSimulator.getTweets(null));
        Assert.assertNull(twitterFeedSimulator.getUsers("badusers.txt"));
        Assert.assertNull(twitterFeedSimulator.getUsers(null));
    }

}
