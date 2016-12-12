package com.example.nicholas.backtoschool.Model;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Nicholas on 12/9/2016.
 */

public class Score {


    private String Type;
    private ArrayList<StudentScore> studentScores=new ArrayList<>();

    public void addStudentScores(StudentScore studentscore){
        studentScores.add(studentscore);
    }
    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }


    public ArrayList<StudentScore> getStudentScores() {
        return studentScores;
    }

    public void setStudentScores(ArrayList<StudentScore> studentScores) {
        this.studentScores = studentScores;
    }
}
