package com.knowmywork.loadmorerecyclerview;

public class Comment {

    //variables
    public String comment;
    public String user;

    //constructor
    public Comment(String comment, String user) {
        this.comment = comment;
        this.user = user;
    }

    //Getter Methods
    public String getUser() {
        return user;
    }

    public String getComment() {
        return comment;
    }

    //Setter Methods
    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
