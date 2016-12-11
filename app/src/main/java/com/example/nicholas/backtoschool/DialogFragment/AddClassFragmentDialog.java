package com.example.nicholas.backtoschool.DialogFragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.nicholas.backtoschool.FirebaseHelper.ClassFirebaseHelper;
import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.example.nicholas.backtoschool.Model.User;
import com.example.nicholas.backtoschool.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Christina on 12/9/2016.
 */

public class AddClassFragmentDialog extends DialogFragment {

    EditText classId;
    Button save, cancel;
    ClassFirebaseHelper cfh;
    FirebaseDatabase fd;
    DatabaseReference dbClass;
    DatabaseReference dbUser;
    FirebaseAuth fba;
    //ArrayList<ClassRoom> classRooms;
    String id = "", email = "";
    String classMasterId = "", crName = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View classView = inflater.inflate(R.layout.fragment_addclass, container, false);
        getDialog().setTitle("Add Class");

        classId = (EditText) classView.findViewById(R.id.classCodetxt);
        save = (Button) classView.findViewById(R.id.add);
        cancel = (Button) classView.findViewById(R.id.cancel);
        fd = FirebaseDatabase.getInstance();
        dbClass = fd.getReference().child("Class");
        dbUser = fd.getReference().child("User");
        cfh = new ClassFirebaseHelper(dbClass);
        fba = FirebaseAuth.getInstance();

        id = fba.getCurrentUser().getUid();
        email = fba.getCurrentUser().getEmail();

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                dbUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            ClassRoom cr = snapshot.getValue(ClassRoom.class);

                            if(cr.getClassRoomID().equals(classId.toString())){

                                classMasterId = cr.getClassMasterID();
                                crName = cr.getClassName();

                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                dbUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            User user = snapshot.getValue(User.class);

                            if(user.getUsername().equals(email)){
                                ClassRoom cr = new ClassRoom();

                                cr.setClassRoomID(classId.toString());
                                cr.setClassMasterID(classMasterId);
                                cr.setClassName(crName);

                                dbUser.child(id).setValue(cr);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                dismiss();
            }
        });

        return classView;
    }
}
