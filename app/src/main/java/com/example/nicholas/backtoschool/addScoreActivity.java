package com.example.nicholas.backtoschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicholas.backtoschool.CustomAdapter.EditScoreAdapter;
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

public class addScoreActivity extends AppCompatActivity {

    ListView listView;
    DatabaseReference db,dbc;
    FirebaseAuth fba;
    String cId;
    Button submit;
    ClassRoom classRoom;
    Score score=new Score();
    ArrayList<String>userID=new ArrayList<>();

    ArrayList<User> users=new ArrayList<>();
    public View getViewByPosition(int pos, ListView a) {
        final int firstListItemPosition = a.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition
                + a.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return a.getAdapter().getView(pos, null, a);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return a.getChildAt(childIndex);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);

        Intent intent = getIntent();
        cId = intent.getStringExtra("classId");

        listView=(ListView)findViewById(R.id.user_score);
        fba=FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance().getReference().child("Users");
        dbc=FirebaseDatabase.getInstance().getReference().child("Class");

        dbc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ClassRoom cr = snapshot.getValue(ClassRoom.class);

                    if(cr.getClassRoomID().equals(cId)){
                        classRoom=cr;
                       }
                }

                db.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userID.clear();
                        users.clear();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            User u = snapshot.getValue(User.class);
                            if(classRoom.getUserID().contains(snapshot.getKey())){
                                userID.add(snapshot.getKey());
                                users.add(u);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        EditScoreAdapter editScoreAdapter=new EditScoreAdapter(getApplicationContext(),users);
        listView.setAdapter(editScoreAdapter);
        submit=(Button)findViewById(R.id.submit_score);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parentView;
                Score score=new Score();
                TextView t=(TextView) findViewById(R.id.scoretype);
                score.setType(t.getText().toString());
                for(int i=0;i<listView.getCount();i++){
                    parentView=getViewByPosition(i,listView);
                    listView.getAdapter().getItem(i);
                    EditText s=(EditText)parentView.findViewById(R.id.score);
                    String StringScore=s.getText().toString();
                    if(Integer.parseInt(StringScore)<0 || Integer.parseInt(StringScore)>100){
                        Toast.makeText(addScoreActivity.this, "You Can't input score more than 100 or less than 0", Toast.LENGTH_SHORT).show();
                    }
                    StudentScore ss=new StudentScore();
                    ss.setScore(Integer.parseInt(StringScore));
                    ss.setStudentName(users.get(i).getName());
                    ss.setStudentID(userID.get(i));
                    score.addStudentScores(ss);


                }
                classRoom.addScore(score);
                dbc.child(classRoom.getClassRoomID()).setValue(classRoom);
                Toast.makeText(addScoreActivity.this, "Success", Toast.LENGTH_SHORT).show();
                finish();

            }
        });


    }
}
