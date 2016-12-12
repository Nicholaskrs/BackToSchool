package com.example.nicholas.backtoschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.widget.ListView;

import com.example.nicholas.backtoschool.CustomAdapter.UserAdapter;
import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.example.nicholas.backtoschool.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentsActivity extends Activity {

    FirebaseAuth fba;
    FirebaseDatabase fd;
    DatabaseReference dbClass;
    List<String> uId = new ArrayList<String>();
    List<User> users = new ArrayList<User>();
    ListView userLists;
    String cId = "";
    ClassRoom currClass = new ClassRoom();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        fd = FirebaseDatabase.getInstance();
        dbClass = fd.getReference().child("Class");
        fba = FirebaseAuth.getInstance();
        userLists = (ListView) findViewById(R.id.userList);

        Intent intent = getIntent();
        cId = intent.getStringExtra("classId");

        dbClass.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ClassRoom cr = snapshot.getValue(ClassRoom.class);

                    if(cr.getClassRoomID().equals(cId)){
                        //uId.add(cr);
                        //users.add(cr.getUsers());

                        currClass = cr;

                        UserAdapter uAdapt = new UserAdapter(getApplicationContext(),(ArrayList<User>) currClass.getUsers());
                        userLists.setAdapter(uAdapt);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

