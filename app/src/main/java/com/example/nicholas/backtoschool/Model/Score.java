package com.example.nicholas.backtoschool.Model;

import java.util.Vector;

/**
 * Created by Nicholas on 12/9/2016.
 */

public class Score {
    String Subject;
    Vector<StudentScore>studentScores;

    public void addStudentScores(StudentScore studentscore){
        studentScores.add(studentscore);
    }
    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public Vector<StudentScore> getStudentScores() {
        return studentScores;
    }

    public void setStudentScores(Vector<StudentScore> studentScores) {
        this.studentScores = studentScores;
    }
}
