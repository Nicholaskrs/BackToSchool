package com.example.nicholas.backtoschool.DialogFragment;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicholas.backtoschool.FirebaseHelper.ClassFirebaseHelper;
import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.example.nicholas.backtoschool.Model.Forum;
import com.example.nicholas.backtoschool.Model.User;
import com.example.nicholas.backtoschool.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Christina on 12/12/2016.
 */
public class AddForumFragmentDialog extends DialogFragment {

    ClassFirebaseHelper cfh;
    FirebaseAuth fba;
    FirebaseDatabase fd;
    DatabaseReference dbClass;
    DatabaseReference dbUser;
    EditText fId, fName, fContent;
    Button add, cancel;
    String uName = "";
    String cId = "";
    ClassRoom classRoom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View forumView = inflater.inflate(R.layout.fragment_addforum, container, false);
        getDialog().setTitle("Add Forum");

        fba = FirebaseAuth.getInstance();
        fd = FirebaseDatabase.getInstance();
        dbClass = fd.getReference();
        dbUser = fd.getReference().child("Users");
        cfh = new ClassFirebaseHelper(dbClass);
        cfh.retrieve();
        fId = (EditText) forumView.findViewById(R.id.forumIdtxt);
        fName = (EditText) forumView.findViewById(R.id.forumTopictxt);
        fContent = (EditText) forumView.findViewById(R.id.forumContenttxt);
        add = (Button) forumView.findViewById(R.id.add);
        cancel = (Button) forumView.findViewById(R.id.cancel);

        Bundle b = getActivity().getIntent().getExtras();
        cId = b.getString("classId");

        //Toast.makeText(forumView.getContext(), "CId = " + cId, Toast.LENGTH_SHORT).show();

        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    if (user.getUsername().equals(fba.getCurrentUser().getEmail())) {
                        uName = user.getName();

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dbClass.child("Class").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ClassRoom tempclass= snapshot.getValue(ClassRoom.class);
                    if(cId.equals(tempclass.getClassRoomID()))
                    {
                        classRoom=tempclass;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean find=false;
                for(Forum frm:classRoom.getForums()){
                    if(frm.getForumID().equals(fId.getText().toString()))
                    {
                        find=true;
                        break;
                    }
                }
                if(find){
                    Toast.makeText(view.getContext(), "Forum Id must be unique please enter enother id", Toast.LENGTH_SHORT).show();
                }
                else if(fName.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "forum name must not be empty", Toast.LENGTH_SHORT).show();
                }
                else if(fContent.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "forum content must not be empty", Toast.LENGTH_SHORT).show();
                }

                else {
                    Forum forum = new Forum();
                    forum.setForumID(fId.getText().toString());
                    forum.setForumTopic(fName.getText().toString());
                    forum.setForumcontent(fContent.getText().toString());
                    forum.setCreateat(new Date(System.currentTimeMillis()));
                    forum.setMadeby(uName);
                    cfh.addnewforum(forum, cId);
                    Toast.makeText(view.getContext(), "Forum added successfully!", Toast.LENGTH_SHORT).show();


                    dismiss();
                }
            }
        });

        return forumView;
    }

}