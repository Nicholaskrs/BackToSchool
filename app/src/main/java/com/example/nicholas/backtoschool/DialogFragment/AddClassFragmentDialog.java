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
    DatabaseReference db;
    FirebaseAuth fba;
    //ArrayList<ClassRoom> classRooms;
    String id = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View classView = inflater.inflate(R.layout.fragment_addclass, container, false);
        getDialog().setTitle("Add Class");

        classId = (EditText) classView.findViewById(R.id.classCodetxt);
        save = (Button) classView.findViewById(R.id.add);
        cancel = (Button) classView.findViewById(R.id.cancel);
        fd = FirebaseDatabase.getInstance();
        db = fd.getReference().child("Class");
        cfh = new ClassFirebaseHelper(db);
        fba = FirebaseAuth.getInstance();

        id = fba.getCurrentUser().getUid();

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            ClassRoom cr = snapshot.getValue(ClassRoom.class);

                            if(cr.getClassRoomID().equals(classId.toString())){

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
