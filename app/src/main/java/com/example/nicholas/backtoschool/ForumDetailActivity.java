package com.example.nicholas.backtoschool;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicholas.backtoschool.CustomAdapter.ReplyAdapter;
import com.example.nicholas.backtoschool.DialogFragment.AddForumFragmentDialog;
import com.example.nicholas.backtoschool.DialogFragment.AddReplyFragmentDialog;
import com.example.nicholas.backtoschool.FirebaseHelper.ClassFirebaseHelper;
import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.example.nicholas.backtoschool.Model.Forum;
import com.example.nicholas.backtoschool.Model.Reply;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ForumDetailActivity extends AppCompatActivity {

    FirebaseAuth fba;
    FirebaseDatabase fd;
    DatabaseReference dbClass;
    ClassFirebaseHelper cfh;
    TextView fTitle, madeBy, fdate, fcontent;
    Button reply;
    ListView replyList;
    String fId = "", cId = "", fDate = "", creator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_detail);

        Intent in = getIntent();
        fId = in.getStringExtra("forumId");
        cId = in.getStringExtra("classId");

        fba = FirebaseAuth.getInstance();
        fd = FirebaseDatabase.getInstance();
        dbClass = fd.getReference().child("Class");
        cfh = new ClassFirebaseHelper(fd.getReference());

        fTitle = (TextView) findViewById(R.id.fTitle);
        madeBy = (TextView) findViewById(R.id.madeBytxt);
        fdate = (TextView) findViewById(R.id.datetxt);
        fcontent = (TextView) findViewById(R.id.contenttxt);
        reply = (Button) findViewById(R.id.replybtn);
        replyList = (ListView) findViewById(R.id.replyList);

        dbClass.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ClassRoom cr = snapshot.getValue(ClassRoom.class);

                    for(Forum forum : cr.getForums()){

                        if(forum.getForumID().equals(fId)){

                            fTitle.setText(forum.getForumTopic());
                            madeBy.setText(forum.getMadeby());

                            DateFormat df = SimpleDateFormat.getDateInstance();
                            fDate = df.format(forum.getCreateat());

                            fdate.setText(fDate);
                            fcontent.setText(forum.getForumcontent());

                            for(Reply reply : forum.getReplies()){

                                final ReplyAdapter ra = new ReplyAdapter(getApplicationContext(), (ArrayList<Reply>) forum.getReplies());
                                replyList.setAdapter(ra);

                            }

                            break;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.FragmentManager fm = getFragmentManager();
                AddReplyFragmentDialog arf = new AddReplyFragmentDialog();
                arf.show(fm, "Add Reply");

                Bundle b = new Bundle();
                b.putString("classId", cId);
                b.putString("forumId", fId);
                arf.setArguments(b);
            }
        });
    }
}
