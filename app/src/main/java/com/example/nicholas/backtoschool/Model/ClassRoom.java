package com.example.nicholas.backtoschool.Model;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicholas on 12/9/2016.
 */

public class ClassRoom {
    private String ClassRoomID="";
    private String ClassMasterID="";
    private String ClassName="";
    private List<Forum> forums=new ArrayList<>();
    private List<Score>scores=new ArrayList<>();
    private List<ClassReminder>reminders=new ArrayList<>();
    private List<User>users=new ArrayList<>();
    private List<String>userID=new ArrayList<>();

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }
    public void addforum(Forum forum){
        forums.add(forum);
    }
    public void adduserid(String userid){
        userID.add(userid);
    }
    public List<String> getUserID() {
        return userID;
    }



    public void addReminder(ClassReminder reminder){
        reminders.add(reminder);
    }
    public List<ClassReminder> getReminders() {
        return reminders;
    }





    public String getClassMasterID() {
        return ClassMasterID;
    }

    public void setClassMasterID(String classMasterID) {
        ClassMasterID = classMasterID;
    }
    public List<User> getUsers() {
        return users;
    }
    public void addUser(User user){
        users.add(user);
    }





    public String getClassRoomID() {
        return ClassRoomID;
    }

    public void setClassRoomID(String classRoomID) {
        ClassRoomID = classRoomID;
    }

    public List<Forum> getForums() {
        return forums;
    }


    public List<Score> getScores() {
        return scores;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public void setReminders(List<ClassReminder> reminders) {
        this.reminders = reminders;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setUserID(List<String> userID) {
        this.userID = userID;
    }

    public ClassRoom(){}
/*
    public ClassRoom(String bikin){
        userID=new ArrayList<String>();
        userID.add("test");
        users=new ArrayList<User>();
        users.add(new User());
        reminders=new ArrayList<ClassReminder>();
        reminders.add(new ClassReminder());
        scores=new ArrayList<Score>();
        scores.add(new Score());
        forums=new ArrayList<Forum>();
        forums.add(new Forum());
    }*/

}
