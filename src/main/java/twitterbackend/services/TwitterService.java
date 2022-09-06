package twitterbackend.services;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.auth.TwitterOAuth20AppOnlyService;
import com.twitter.clientlib.model.*;
import twitterbackend.entities.UserTweet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import twitterbackend.repositories.TweetRepository;
//import twitterbackend.repositories.UserRepository;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TwitterService {

    //private static final String _bearerToken = "";
    private static final Logger log = LogManager.getLogger(TwitterService.class);

    TweetRepository tweetRepository;
    //UserRepository userRepository;

    public TwitterService(TweetRepository tweetRepository)//, UserRepository userRepository)
    {
        this.tweetRepository = tweetRepository;
        //this.userRepository = userRepository;
    }

    //adds a list of tweets from a given twitter user to the database and returns the list
    public List<UserTweet> addTweets(String userID) {
        String bearerToken = System.getenv("bearertoken");
        //Initiate twitter api with dev platform bearer token
        TwitterApi api = new TwitterApi(new TwitterCredentialsBearer(bearerToken));
        //Find the twitter user based on supplied userID
        User twitterUser = getTwitterUserByID(api, userID);
        //TODO Convert from twitter's user model to our backend model
        //UserProfile userProfile = createUser(twitterUser);

        //Store a list of the user's tweets
        List<Tweet> twitterUserTweets = getUserTweets(api, twitterUser);
        //Convert from twitter's tweet model to our backend model
        List<UserTweet> tweetList = createTweetList(api, twitterUserTweets, false);

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

    public boolean existsByID(int id) {
        return tweetRepository.existsById(id);
    }

    public UserTweet manualAdd(UserTweet userTweet) {
        return tweetRepository.save(userTweet);
    }

    private User getTwitterUserByID(TwitterApi api, String id) {
        User foundUser = null;

        try {
            //initiate user search by ID
            Get2UsersIdResponse userResult = api.users().findUserById(id)
                    .execute();

            if (userResult.getErrors() != null && userResult.getErrors().size() > 0) {
                log.error("Twitter user search errors returned " + userResult.getErrors().size() + " results");
                userResult.getErrors().forEach(e -> {
                    log.error(e.toString());
                    if (e instanceof ResourceUnauthorizedProblem) {
                        log.error(e.getTitle() + " " + e.getDetail());
                    }
                });

            } else{
                foundUser = userResult.getData(); //set the found user if no errors are found
            }
        } catch (ApiException e) {
            log.error("Unable to find user: " + e);
            e.printStackTrace();
        }

        return foundUser;
    }

    //returns list of supplied user's tweets within given search parameters.
    //search params: max results = 25; date range = {current time -> (current time - 7 days)}
    private List<com.twitter.clientlib.model.Tweet> getUserTweets (TwitterApi api, User user) {
        List<com.twitter.clientlib.model.Tweet> tweetList = new ArrayList<>();
        int maxResults = 25;

        //date seconds must be truncated to avoid twitter date format error
        OffsetDateTime endTime = OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        OffsetDateTime startTime = endTime.minusDays(7L); //subtract 7 days from current date

        //list of requested attributes to be returned with all tweets. Unlisted attributes return as null
        Set<String> tweetFields = new HashSet<>();
        tweetFields.add("id");
        tweetFields.add("author_id");
        tweetFields.add("conversation_id");
        tweetFields.add("created_at");
        tweetFields.add("in_reply_to_user_id");
        tweetFields.add("lang");
        tweetFields.add("source");

        Get2UsersIdTweetsResponse tweetsResult = null;

        try {
            //initiate tweet search by userID within given search parameters
            tweetsResult = api.tweets().usersIdTweets(user.getId())
                    .maxResults(maxResults)
                    .startTime(startTime)
                    .endTime(endTime)
                    .tweetFields(tweetFields)
                    .execute();


            if (tweetsResult.getErrors() != null && tweetsResult.getErrors().size() > 0) {
                log.error("Twitter user timeline search errors returned " + tweetsResult.getErrors().size() + " results");
                tweetsResult.getErrors().forEach(e -> {
                    log.error(e.toString());
                    if (e instanceof ResourceUnauthorizedProblem) {
                        log.error(e.getTitle() + " " + e.getDetail());
                    }
                });
            } else {
                //add each of the user's tweets to return list if no errors are found
                for(com.twitter.clientlib.model.Tweet userTweet : tweetsResult.getData()) {
                    tweetList.add(userTweet);
                }
            }
        } catch (ApiException e) {
            log.error("Unable to find user tweets: " + e);
            e.printStackTrace();
        }

        return tweetList;
    }

    //returns a list of recent responses to a tweet
    //without academic research access on the developer account, only recent search is usable
    private List<UserTweet> getRecentResponses (TwitterApi api, String conversationID) {
        List<UserTweet> tweetList = new ArrayList<>();

        int maxResults = 25;

        Set<String> tweetFields = new HashSet<>();
        tweetFields.add("id");
        tweetFields.add("author_id");
        tweetFields.add("conversation_id");
        tweetFields.add("created_at");
        tweetFields.add("in_reply_to_user_id");
        tweetFields.add("lang");
        tweetFields.add("source");

        try {
            //initiate tweet response search with a query containing supplied conversationID
            Get2TweetsSearchRecentResponse tweetsResult = api.tweets().tweetsRecentSearch("conversation_id:" + conversationID)
                    .maxResults(maxResults)
                    .tweetFields(tweetFields)
                    .execute();

            if (tweetsResult.getErrors() != null && tweetsResult.getErrors().size() > 0) {
                log.error("Tweet response search errors returned " + tweetsResult.getErrors().size() + " results");
                tweetsResult.getErrors().forEach(e -> {
                    log.error(e.toString());
                    if (e instanceof ResourceUnauthorizedProblem) {
                        log.error(e.getTitle() + " " + e.getDetail());
                    }
                });
            } else {
                //create a local tweet model list if no errors are found
                tweetList = createTweetList(api, tweetsResult.getData(), true);
            }
        } catch (ApiException e) {
            log.error("Unable to find user tweets: " + e);
            e.printStackTrace();
        }

        return tweetList;
    }

    //convert list of Twitter tweet model to local tweet model
    //searches for recent tweet responses if isResponse flag is false
    private List<UserTweet> createTweetList (TwitterApi api, List<com.twitter.clientlib.model.Tweet> tweets, boolean isResponse) {
        List<UserTweet> tweetList = new ArrayList<>();

        //TODO implement tweet response grabbing
        for (com.twitter.clientlib.model.Tweet userTweet : tweets) {
            if(isResponse) //does not search for responses
                tweetList.add(new UserTweet(userTweet.getId(), userTweet.getText(), userTweet.getConversationId(), userTweet.getCreatedAt(), userTweet.getAuthorId(), userTweet.getInReplyToUserId(), userTweet.getLang(), userTweet.getSource()));
            else //generates list of responses to an initial tweet
                tweetList.add(new UserTweet(userTweet.getId(), userTweet.getText(), userTweet.getConversationId(), userTweet.getCreatedAt(), userTweet.getAuthorId(), userTweet.getInReplyToUserId(), userTweet.getLang(), userTweet.getSource()));
        }

        return tweetList;
    }

    public OAuth2AccessToken getAccessToken() {
        TwitterOAuth20AppOnlyService service = new TwitterOAuth20AppOnlyService(
                "",
                "");

        OAuth2AccessToken accessToken = null;
        try {
            accessToken = service.getAccessTokenClientCredentialsGrant();

            System.out.println("Access token: " + accessToken.getAccessToken());
            System.out.println("Token type: " + accessToken.getTokenType());
        } catch (Exception e) {
            System.err.println("Error while getting the access token:\n " + e);
            e.printStackTrace();
        }
        return accessToken;
    }
}
