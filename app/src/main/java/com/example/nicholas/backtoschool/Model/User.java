package com.example.nicholas.backtoschool.Model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Nicholas on 12/3/2016.
 */
public class User {

    private String name,username,school,educationalLevel,gender,studentNumber;
    private int age;
    private List<ClassReminder> schedule;
    private List<ClassRoom> classRooms = new ArrayList<>();

    public void addclassroom(ClassRoom classroom){
        classRooms.add(classroom);
    }
    public void addreminder(ClassReminder classReminder){
        schedule.add(classReminder);
    }
    public List<ClassReminder> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<ClassReminder> schedule) {
        this.schedule = schedule;
    }

    public List<ClassRoom> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(ArrayList<ClassRoom> classRooms) {
        this.classRooms = classRooms;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEducationalLevel() {

        return educationalLevel;
    }

    public void setEducationalLevel(String educationalLevel) {
        this.educationalLevel = educationalLevel;
    }

    public String getSchool() {

        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }


    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User()
    {

    }


    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
