package com.example.nicholas.backtoschool.DialogFragment;

import android.app.DialogFragment;
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
import com.example.nicholas.backtoschool.Model.User;
import com.example.nicholas.backtoschool.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

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
    ClassRoom cr;
    FirebaseAuth fba;
    String id = "", email = "";
    String classMasterId = "", crName = "", cId = "";
    ArrayList<ClassRoom> classRoom = new ArrayList<ClassRoom>();
    ArrayList<User> users = new ArrayList<User>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View classView = inflater.inflate(R.layout.fragment_addclass, container, false);
        getDialog().setTitle("Add Class");

        classId = (EditText) classView.findViewById(R.id.classCodetxt);
        save = (Button) classView.findViewById(R.id.add);
        cancel = (Button) classView.findViewById(R.id.cancel);
        fd = FirebaseDatabase.getInstance();
        dbClass = fd.getReference().child("Class");
        dbUser = fd.getReference().child("Users");
        cfh = new ClassFirebaseHelper(dbClass);
        fba = FirebaseAuth.getInstance();

        id = fba.getCurrentUser().getUid();
        email = fba.getCurrentUser().getEmail();

        dbClass.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ClassRoom cr = snapshot.getValue(ClassRoom.class);

                    classRoom.add(cr);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    if (user.getUsername().equals(email)) {

                        users.add(user);

                        break;
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                cId = classId.getText().toString();

                for(ClassRoom cr : classRoom){

                    if(cr.getClassRoomID().equals(cId)){
                        crName = cr.getClassName();
                        classMasterId = cr.getClassMasterID();

                        break;
                    }

                }
                if(crName.equals(""))
                {
                    Toast.makeText(view.getContext(), "Class room not found", Toast.LENGTH_SHORT).show();
                }else {

                    cr = new ClassRoom();
                    cr.setClassRoomID(cId);
                    cr.setClassMasterID(classMasterId);
                    cr.setClassName(crName);

                    for (User user : users) {
                        if (user.getUsername().equals(email)) {
                            boolean already=false;
                            for(int i=0;i<user.getClassRooms().size();i++){
                                if(user.getClassRooms().get(i).getClassRoomID().equals(cId)) {
                                already=true;
                                    break;
                                }
                            }
                            if(already){
                                Toast.makeText(view.getContext(), "You are already in the class!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                user.addclassroom(cr);
                                dbUser.child(id).setValue(user);
                                cfh.adduser(classId.toString(), id, user);
                                Toast.makeText(view.getContext(), "user: " + user.getUsername(), Toast.LENGTH_SHORT).show();

                                break;
                            }
                        }
                    }

                    Toast.makeText(view.getContext(), "Class room added successfully!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });

        return classView;
    }
}
