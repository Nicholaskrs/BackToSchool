package com.example.nicholas.backtoschool;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nicholas.backtoschool.FirebaseHelper.ClassFirebaseHelper;
import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClassDetailActivity extends Activity {

    TextView classTitle, classId, lecturer;
    Button activity, forum, viewScore, students, addScore;
    ClassFirebaseHelper cfh;
    FirebaseDatabase fd;
    DatabaseReference dbClass;
    FirebaseAuth fba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        fd = FirebaseDatabase.getInstance();
        dbClass = fd.getReference().child("Class");
        cfh = new ClassFirebaseHelper(dbClass);
        fba = FirebaseAuth.getInstance();

        classTitle = (TextView) findViewById(R.id.classTitleLbl);
        classId = (TextView) findViewById(R.id.classidtxt);
        lecturer = (TextView) findViewById(R.id.lecturetxt);
        activity = (Button) findViewById(R.id.addActivity);
        forum = (Button) findViewById(R.id.viewForum);
        viewScore = (Button) findViewById(R.id.viewScore);
        students = (Button) findViewById(R.id.viewStudents);
        addScore = (Button) findViewById(R.id.addScore);

        dbClass.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ClassRoom cr = snapshot.getValue(ClassRoom.class);

                    if(cr.getClassRoomID().equals("")){

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
