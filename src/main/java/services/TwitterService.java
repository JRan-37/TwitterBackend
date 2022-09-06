package services;

import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.Tweet;
import com.twitter.clientlib.model.User;
import entities.UserTweet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import repositories.TweetRepository;
import repositories.UserRepository;

import java.util.List;

@Service
public class TwitterService {

    private static final String _bearerToken = "";
    private static final Logger log = LogManager.getLogger(TwitterService.class);

    TweetRepository tweetRepository;
    UserRepository userRepository;

    public TwitterService(TweetRepository tweetRepository, UserRepository userRepository)
    {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    //adds a list of tweets from a given twitter user to the database and returns the list
    public List<UserTweet> addTweets(String userID) {
        //Initiate twitter api with dev platform bearer token
        TwitterApi api = new TwitterApi(new TwitterCredentialsBearer(_bearerToken));
        //Find the twitter user based on supplied userID
        User twitterUser = getTwitterUserByID(api, userID);
        //TODO Convert from twitter's user model to our backend model
        //UserProfile userProfile = createUser(twitterUser);

        //Store a list of the user's tweets
        List<Tweet> twitterUserTweets = getUserTweets(api, twitterUser);
        //Convert from twitter's tweet model to our backend model
        List<UserTweet> tweetList = createTweetList(twitterUserTweets);

        //TODO Save user to database
        //userRepository.save(userProfile);

        //Save tweets to database
        for(UserTweet tweet : tweetList)
            tweetRepository.save(tweet);

        return tweetList;
    }

    public List<UserTweet> getTweets(String userID) {
        List<UserTweet> tweetList = null;
        //TODO return tweets from database
        return tweetList;
    }

    private User getTwitterUserByID(TwitterApi api, String userID) {
        User foundUser = null;
        //TODO implement user finding algorithm
        return foundUser;
    }

    private List<Tweet> getUserTweets(TwitterApi api, User twitterUser) {
        List<Tweet> foundTweets = null;
        //TODO implement tweet finding algorithm
        return foundTweets;
    }

    private List<UserTweet> createTweetList(List<Tweet> twitterUserTweets) {
        List<UserTweet> convertedTweets = null;
        //TODO implement tweet model conversion
        return convertedTweets;
    }
}
