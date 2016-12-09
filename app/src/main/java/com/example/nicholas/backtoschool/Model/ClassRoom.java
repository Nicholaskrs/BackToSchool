package com.example.nicholas.backtoschool.Model;

import java.util.Vector;

/**
 * Created by Nicholas on 12/9/2016.
 */

public class ClassRoom {
    private String ClassRoomID,ClassMasterID;
    private Vector<Forum> forums;
    private Vector<Score>scores;
    private Vector<ClassReminder>reminders;
    private Vector<User>users;
    private Vector<String>userID;

    public void adduserid(String userid){
        userID.add(userid);
    }
    public Vector<String> getUserID() {
        return userID;
    }

    public void setUserID(Vector<String> userID) {
        this.userID = userID;
    }

    public void addReminder(ClassReminder reminder){
        reminders.add(reminder);
    }
    public Vector<ClassReminder> getReminders() {
        return reminders;
    }

    public void setReminders(Vector<ClassReminder> reminders) {
        this.reminders = reminders;
    }



    public String getClassMasterID() {
        return ClassMasterID;
    }

    public void setClassMasterID(String classMasterID) {
        ClassMasterID = classMasterID;
    }
    public Vector<User> getUsers() {
        return users;
    }
    public void addUser(User user){
        users.add(user);
    }

    public void setUsers(Vector<User> users) {
        this.users = users;
    }



    public String getClassRoomID() {
        return ClassRoomID;
    }

    public void setClassRoomID(String classRoomID) {
        ClassRoomID = classRoomID;
    }

    public Vector<Forum> getForums() {
        return forums;
    }

    public void setForums(Vector<Forum> forums) {
        this.forums = forums;
    }

    public Vector<Score> getScores() {
        return scores;
    }

    public void setScores(Vector<Score> scores) {
        this.scores = scores;
    }





    public ClassRoom(){

    }

}
