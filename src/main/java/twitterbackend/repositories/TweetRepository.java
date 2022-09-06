package twitterbackend.repositories;

import twitterbackend.entities.UserTweet;
import org.springframework.data.repository.CrudRepository;

public interface TweetRepository extends CrudRepository<UserTweet, Integer> {

}
