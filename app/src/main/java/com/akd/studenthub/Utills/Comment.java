package com.akd.studenthub.Utills;

public class Comment {
    private String username;
    private String profileImageUrl;
    private String comment;

    public String getCommentUserUid() {
        return commentUserUid;
    }

    public void setCommentUserUid(String commentUserUid) {
        this.commentUserUid = commentUserUid;
    }

    public Comment(String commentUserUid) {
        this.commentUserUid = commentUserUid;
    }

    private String commentUserUid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Comment() {

    }

    public Comment(String username, String profileImageUrl, String comment) {

        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.comment = comment;
    }
}
