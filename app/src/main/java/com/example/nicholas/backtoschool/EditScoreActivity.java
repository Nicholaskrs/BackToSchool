package com.example.nicholas.backtoschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.nicholas.backtoschool.CustomAdapter.ScoreTitle;
import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditScoreActivity extends AppCompatActivity {

    Button add;
    String cId;
    ListView listView;
    DatabaseReference db;
    ClassRoom cr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_score);
        Intent intent = getIntent();

        cId = intent.getStringExtra("classId");
        db= FirebaseDatabase.getInstance().getReference().child("Class");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ClassRoom temp = snapshot.getValue(ClassRoom.class);
                    if(temp.getClassRoomID().equals(cId)){
                        cr=temp;
                        ScoreTitle st=new ScoreTitle(getApplicationContext(),(ArrayList)cr.getScores());
                        listView.setAdapter(st);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView= (ListView)findViewById(R.id.editscore);

        add =(Button)findViewById(R.id.addscore);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditScoreActivity.this, addScoreActivity.class);
                intent.putExtra("classId", cId);
                startActivity(intent);
            }
        });
    }
}
