package com.example.nicholas.backtoschool.DialogFragment;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicholas.backtoschool.FirebaseHelper.ClassFirebaseHelper;
import com.example.nicholas.backtoschool.FirebaseHelper.UserFirebaseHelper;
import com.example.nicholas.backtoschool.Model.ClassReminder;
import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.example.nicholas.backtoschool.Model.Forum;
import com.example.nicholas.backtoschool.Model.Reply;
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
import java.util.Vector;

/**
 * Created by Nicholas on 12/10/2016.
 */

public class MakeClassFragmentDialog extends DialogFragment {
    Button save, cancel;
    EditText classId,className;
    ClassFirebaseHelper cfh;
    DatabaseReference db,dbu;

    FirebaseAuth mfauth;
    ArrayList<ClassRoom> classRooms;
    Context context;
ArrayList<User> users;
    User publicuser=new User();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mfauth=FirebaseAuth.getInstance();

        View classView = inflater.inflate(R.layout.fragment_makeclass, container, false);
        context=classView.getContext();
        getDialog().setTitle("Make Class");
        db = FirebaseDatabase.getInstance().getReference();
        cfh=new ClassFirebaseHelper(db);
        dbu= db.child("Users");
        dbu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    if(user!=null&&user.getUsername().equals(mfauth.getCurrentUser().getEmail())){

                        publicuser=user;


                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        classRooms = new ArrayList<>();
        cfh.retrieve();

        classId=(EditText)classView.findViewById(R.id.ClassRoomID);
        className=(EditText)classView.findViewById(R.id.ClassName);
        save = (Button) classView.findViewById(R.id.make_class);
        cancel = (Button) classView.findViewById(R.id.make_class_cancel);

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            boolean find=false;
            classRooms=cfh.getClassRooms();

            for(int i=0;i<classRooms.size();i++) {
                if(classRooms.get(i).getClassRoomID().equals(classId.getText().toString().trim())) {
                    find = true;
                    break;
                }
            }
            if(find){
                Toast.makeText(context, "Class ID already used please enter another ID", Toast.LENGTH_SHORT).show();
            }
            else if(classId.getText().toString().trim().equals("")||className.getText().toString().trim().equals("")){
                Toast.makeText(context, "ID or Name must be filled", Toast.LENGTH_SHORT).show();
            }
            else{
                ClassRoom classroom=new ClassRoom();
                classroom.setClassMasterID(mfauth.getCurrentUser().getUid());
                classroom.setClassRoomID(classId.getText().toString().trim());
                classroom.setClassName(className.getText().toString().trim());



                publicuser.addclassroom(classroom);
                dbu.child(mfauth.getCurrentUser().getUid()).setValue(publicuser);
                //classroom.addUser(publicuser);
                classroom.adduserid(mfauth.getCurrentUser().getUid());





                User classmates=new User();
                classmates.setUsername(publicuser.getUsername());
                classmates.setName(publicuser.getName());
                classmates.setGender(publicuser.getGender());
                classmates.setEducationalLevel(publicuser.getEducationalLevel());
                classmates.setAge(publicuser.getAge());

                classroom.addUser(classmates);
                String kata=cfh.addclassroom(classroom);
                Toast.makeText(context, kata, Toast.LENGTH_SHORT).show();


                dismiss();
            }



            }
        });

        return classView;
       
    }
}
