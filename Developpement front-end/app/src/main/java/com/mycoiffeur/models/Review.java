package com.mycoiffeur.models;

public class Review {

    private String reviewId;
    private String clientId;
    private String profileId;
    private String feedBack;
    private String firstName,lastName;
    private int note;

    public Review(){

    }

    public Review(String reviewId, String clientId, String profileId, String feedBack, int note) {
        this.reviewId = reviewId;
        this.clientId = clientId;
        this.profileId = profileId;
        this.feedBack = feedBack;
        this.note = note;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }
}
