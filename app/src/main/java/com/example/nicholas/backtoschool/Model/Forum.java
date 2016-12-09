package com.example.nicholas.backtoschool.Model;

import java.util.Date;
import java.util.Vector;

/**
 * Created by Nicholas on 12/9/2016.
 */

public class Forum {
    private String forumTopic,forumID,madeby,forumcontent;
    private Vector<Reply> replies;
    private Date createat;

    public Date getCreateat() {
        return createat;
    }

    public void setCreateat(Date createat) {
        this.createat = createat;
    }

    public String getForumTopic() {
        return forumTopic;
    }

    public void setForumTopic(String forumTopic) {
        this.forumTopic = forumTopic;
    }

    public String getForumID() {
        return forumID;
    }

    public void setForumID(String forumID) {
        this.forumID = forumID;
    }

    public String getMadeby() {
        return madeby;
    }

    public void setMadeby(String madeby) {
        this.madeby = madeby;
    }

    public String getForumcontent() {
        return forumcontent;
    }

    public void setForumcontent(String forumcontent) {
        this.forumcontent = forumcontent;
    }

    public Vector<Reply> getReplies() {
        return replies;
    }

    public void setReplies(Vector<Reply> replies) {
        this.replies = replies;
    }
    public void addReplies(Reply reply){
        replies.add(reply);
    }
}
