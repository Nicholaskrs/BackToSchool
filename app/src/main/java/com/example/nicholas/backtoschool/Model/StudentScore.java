package com.example.nicholas.backtoschool.Model;

/**
 * Created by Nicholas on 12/9/2016.
 */

public class StudentScore {
    private String studentName,StudentNumber;
    private int score;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        StudentNumber = studentNumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

