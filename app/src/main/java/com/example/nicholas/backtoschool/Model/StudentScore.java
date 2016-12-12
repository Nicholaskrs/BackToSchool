package com.example.nicholas.backtoschool.Model;

import android.text.Editable;

/**
 * Created by Nicholas on 12/9/2016.
 */

public class StudentScore {
    private String studentName,StudentID;
    private int score;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

