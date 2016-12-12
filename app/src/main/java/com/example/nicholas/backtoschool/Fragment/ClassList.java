package com.example.nicholas.backtoschool.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nicholas.backtoschool.ClassDetailActivity;
import com.example.nicholas.backtoschool.CustomAdapter.ClassListAdapter;
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
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassList extends Fragment {
    FirebaseAuth fba;
    DatabaseReference db;
    ArrayList<ClassRoom>classRooms=new ArrayList<>();
    User curruser=new User();
    public ClassList() {

        // Required empty public constructor

    }
    ListView listView ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseAuth fba= FirebaseAuth.getInstance();
        DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users");
        View view=inflater.inflate(R.layout.fragment_class_list, container, false);
        listView= (ListView) view.findViewById(R.id.listView);

        final String email=fba.getCurrentUser().getEmail();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    User user = snapshot.getValue(User.class);


                    if (user.getUsername().equals(email)) {

                        curruser = user;
                        //Toast.makeText(getContext(),curruser.getName(), Toast.LENGTH_SHORT).show();
                        final ClassListAdapter classAdapter = new ClassListAdapter(getContext(),curruser.getClassRooms());
                        listView.setAdapter(classAdapter);
                        //TODO notes: Done -CX
                        //TODO notes: -View students (Gw lg kerja yg ini ya) -Add activity -View forum -View score -Add Score -View Score -Facebook
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                        {
                            @Override
                            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
                            {
                                int pos=position+1;

                                Intent intent = new Intent(view.getContext(), ClassDetailActivity.class);
                                intent.putExtra("classId", ((ClassRoom)classAdapter.getItem(position)).getClassRoomID());
                                startActivity(intent);

                                //Toast.makeText(view.getContext(), ((ClassRoom)classAdapter.getItem(position)).getClassRoomID()+" Clicked", Toast.LENGTH_SHORT).show();
                            }

                        });
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "dabatase error", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

}
