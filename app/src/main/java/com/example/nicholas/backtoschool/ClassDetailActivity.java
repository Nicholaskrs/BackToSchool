package com.example.nicholas.backtoschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nicholas.backtoschool.DialogFragment.AddActivityFragmentDialog;
import com.example.nicholas.backtoschool.FirebaseHelper.ClassFirebaseHelper;
import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.example.nicholas.backtoschool.Model.User;
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
    ClassRoom classRoom;
    String className = "", cId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        Intent intent = getIntent();

        cId = intent.getStringExtra("classId");


        fd = FirebaseDatabase.getInstance();
        dbClass = fd.getReference().child("Class");
        cfh = new ClassFirebaseHelper(dbClass);
        fba = FirebaseAuth.getInstance();

        classTitle = (TextView) findViewById(R.id.classTitleLbl);
        classId = (TextView) findViewById(R.id.classidtxt);
        activity = (Button) findViewById(R.id.addActivity);
        forum = (Button) findViewById(R.id.viewForum);
        viewScore = (Button) findViewById(R.id.viewScore);
        students = (Button) findViewById(R.id.viewStudents);

        dbClass.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ClassRoom cr = snapshot.getValue(ClassRoom.class);

                    if(cr.getClassRoomID().equals(cId)){
                        classRoom=cr;
                        classTitle.setText(cr.getClassName());
                        classId.setText(cId);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       activity.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               android.app.FragmentManager fm = getFragmentManager();
               AddActivityFragmentDialog aaf=new AddActivityFragmentDialog();
               aaf.show(fm,"Add Activity");
               Bundle b=new Bundle();
               b.putString("ClassID",cId);

               aaf.setArguments(b);

           }
       });

        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ClassDetailActivity.this, ViewForumActivity.class);
                intent1.putExtra("classId", cId);
                startActivity(intent1);
            }
        });

        viewScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(fba.getCurrentUser().getUid().equals(classRoom.getClassMasterID())){
                    intent = new Intent(ClassDetailActivity.this,EditScoreActivity.class);
                }
                else{
                    intent = new Intent(ClassDetailActivity.this, MyScoreActivity.class);
                }

                intent.putExtra("classId", classRoom.getClassRoomID().toString());
                startActivity(intent);

            }
        });

        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassDetailActivity.this, ViewStudentsActivity.class);
                intent.putExtra("classId", cId);
                startActivity(intent);
            }
        });

    }
}
