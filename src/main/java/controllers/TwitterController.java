package controllers;

import entities.UserTweet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import services.TwitterService;

import java.util.List;

@Controller
public class TwitterController {

    TwitterService twitterService;

    public TwitterController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @PostMapping("/tweets/by-user/{id}")
    public List<UserTweet> addTweetsByUserID(@PathVariable String id) {
        return this.twitterService.addTweets(id);
    }

    @GetMapping("/tweets/by-user/{id}")
    public List<UserTweet> getTweetsByUserID(@PathVariable String id) {
        return twitterService.getTweets(id);
    }

    //TODO tweet and comment posting
    /*@PostMapping("/tweets/new-post")
    public UserTweet postTweet(@RequestBody TweetRequest tweetRequest) {
        return twitterService.newTweet(tweetRequest);
    }

    @PostMapping("/tweets/comment")
    public UserTweet postComment(@RequestBody CommentRequest commentRequest) {
        return twitterService.newComment(commentRequest);
    }*/
}
