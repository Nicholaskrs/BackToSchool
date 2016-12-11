package com.example.nicholas.backtoschool;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nicholas.backtoschool.CustomAdapter.ClassListAdapter;
import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.example.nicholas.backtoschool.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassList extends Fragment {

ArrayList<ClassRoom>classRooms=new ArrayList<>();
    User curruser=new User();
    public ClassList() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_class_list, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        FirebaseAuth fba= FirebaseAuth.getInstance();
        DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("User");
        final String email=fba.getCurrentUser().getEmail();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    if (user.getUsername().equals(email)) {
                        curruser = user;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //ClassListAdapter classAdapter = new ClassListAdapter(getContext(),curruser.getClassRooms());
        //listView.setAdapter(classAdapter);
        return view;
    }

}
