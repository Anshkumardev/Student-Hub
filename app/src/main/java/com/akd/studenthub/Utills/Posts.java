package com.akd.studenthub.Utills;

public class Posts {

    private String postDate;
    private String postDesc;
    private String postImageURL;
    private String userProfileImageURL;
    private String username;

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public Posts(String UID) {
        this.UID = UID;
    }

    private String UID;

    public Posts() {
    }

    public Posts(String postDate, String postDesc, String postImageURL, String userProfileImageURL, String username) {
        this.postDate = postDate;
        this.postDesc = postDesc;
        this.postImageURL = postImageURL;
        this.userProfileImageURL = userProfileImageURL;
        this.username = username;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getPostImageURL() {
        return postImageURL;
    }

    public void setPostImageURL(String postImageURL) {
        this.postImageURL = postImageURL;
    }

    public String getUserProfileImageURL() {
        return userProfileImageURL;
    }

    public void setUserProfileImageURL(String userProfileImageURL) {
        this.userProfileImageURL = userProfileImageURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
