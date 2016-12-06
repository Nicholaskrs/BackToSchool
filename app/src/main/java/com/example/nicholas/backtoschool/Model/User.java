package com.example.nicholas.backtoschool.Model;

/**
 * Created by Nicholas on 12/3/2016.
 */
public class User {

    private String name,username,school,educationalLevel,gender,studentNumber;
    private int age;

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
