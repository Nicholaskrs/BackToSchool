package com.example.nicholas.backtoschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.nicholas.backtoschool.CustomAdapter.ScoreAdapter;
import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.example.nicholas.backtoschool.Model.Score;
import com.example.nicholas.backtoschool.Model.StudentScore;
import com.example.nicholas.backtoschool.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyScoreActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> types=new ArrayList<>();
    ArrayList<StudentScore> studentScores=new ArrayList<>();
    FirebaseAuth fba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_score);
        System.out.println("Activity My Score");
        Intent intent = getIntent();

        String cId = intent.getStringExtra("classId");
        fba= FirebaseAuth.getInstance();
        DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Class");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ClassRoom cr = snapshot.getValue(ClassRoom.class);
                    for(Score score:cr.getScores()){
                        types.add(score.getType());
                        for(StudentScore ss: score.getStudentScores()){
                            if(ss.getStudentID().equals(fba.getCurrentUser().getUid())) {
                                studentScores.add(ss);
                                ScoreAdapter scoreAdapter=new ScoreAdapter(MyScoreActivity.this,studentScores,types);
                                listView.setAdapter(scoreAdapter);
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView=(ListView)findViewById(R.id.my_score);

    }
}
