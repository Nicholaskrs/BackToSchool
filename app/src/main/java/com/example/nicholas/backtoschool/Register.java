package com.example.nicholas.backtoschool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {

    Spinner grade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        grade.findViewById(R.id.cmbLevel);
        List<String> grades=new ArrayList<String>();
        grades.add("Elementary School");
        grades.add("Junior High School");
        grades.add("Senior High School");
        grades.add("University");

         ArrayAdapter<String> gradesadapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,grades);
        gradesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          grade.setAdapter(gradesadapter);


    }
}
