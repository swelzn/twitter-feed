package com.zeromargin;

import com.zeromargin.service.TwitterFeedSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private static final String USER_FILE = "user.txt";
    private static final String TWEET_FILE = "tweet.txt";

    @Autowired
    private TwitterFeedSimulator twitterFeedSimulator;

    @Override
    public void run(String... strings) {
        logger.info("Start Twitter Feed application...");
        twitterFeedSimulator.simulate(USER_FILE, TWEET_FILE);
        logger.info("End Twitter Feed application.");
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
