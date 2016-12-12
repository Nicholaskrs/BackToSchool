package com.example.nicholas.backtoschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nicholas.backtoschool.CustomAdapter.ForumAdapter;
import com.example.nicholas.backtoschool.DialogFragment.AddClassFragmentDialog;
import com.example.nicholas.backtoschool.DialogFragment.AddForumFragmentDialog;
import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.example.nicholas.backtoschool.Model.Forum;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewForumActivity extends AppCompatActivity {

    FirebaseAuth fba;
    FirebaseDatabase fd;
    DatabaseReference dbClass;
    Button addForum;
    ListView forumList;
    String cId = "";
    //Forum currForum = new Forum();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forum);

        fba = FirebaseAuth.getInstance();
        fd = FirebaseDatabase.getInstance();
        dbClass = fd.getReference().child("Class");
        addForum = (Button) findViewById(R.id.add_forum);
        forumList = (ListView) findViewById(R.id.forumList);

        Intent intent = getIntent();
        cId = intent.getStringExtra("classId");

        dbClass.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ClassRoom cr = snapshot.getValue(ClassRoom.class);

                    if (cr.getClassRoomID().equals(cId)) {
                        final ForumAdapter fa = new ForumAdapter(getApplicationContext(), (ArrayList<Forum>) cr.getForums());
                        forumList.setAdapter(fa);

                        forumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                //int pos = i + 1;

                                Intent in = new Intent(getApplicationContext(), ForumDetailActivity.class);
                                in.putExtra("forumId", ((Forum) fa.getItem(i)).getForumID());
                                in.putExtra("classId", cId);
                                startActivity(in);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        addForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.FragmentManager fm = getFragmentManager();
                AddForumFragmentDialog afd = new AddForumFragmentDialog();
                afd.show(fm, "Add Forum");

                Bundle b = new Bundle();
                b.putString("classId", cId);
                afd.setArguments(b);
            }
        });

    }
}
