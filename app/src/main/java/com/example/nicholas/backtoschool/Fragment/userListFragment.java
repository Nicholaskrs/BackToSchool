package com.example.nicholas.backtoschool.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nicholas.backtoschool.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class userListFragment extends Fragment {

    ListView listView ;
    public userListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseAuth fba= FirebaseAuth.getInstance();
        DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Class");
        View view=inflater.inflate(R.layout.fragment_class_list, container, false);
        listView= (ListView) view.findViewById(R.id.listView);
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

}
