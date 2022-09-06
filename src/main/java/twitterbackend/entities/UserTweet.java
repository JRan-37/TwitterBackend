package twitterbackend.entities;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "tweets")
public class UserTweet {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column
    private String text;
    @Column
    private String conversationID;
    @Column
    private OffsetDateTime createdAt;
    @Column
    private String userID;
    @Column
    private String toUserID;
    @Column
    private String language;
    @Column
    private String source;

    public UserTweet() {

    }

    public UserTweet(String id, String text, String conversationID, OffsetDateTime createdAt, String userID, String toUserID, String language, String source) {
        this.id = id;
        this.text = text;
        this.conversationID = conversationID;
        this.createdAt = createdAt;
        this.userID = userID;
        this.toUserID = toUserID;
        this.language = language;
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getToUserID() {
        return toUserID;
    }

    public void setToUserID(String toUserID) {
        this.toUserID = toUserID;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
