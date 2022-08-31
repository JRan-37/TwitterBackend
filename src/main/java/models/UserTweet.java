package models;

import java.time.OffsetDateTime;

public class UserTweet {

    private String id;
    private String text;
    private String conversationID;
    private OffsetDateTime createdAt;
    private String userID;
    private String toUserID;
    private String language;
    private String source;

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
