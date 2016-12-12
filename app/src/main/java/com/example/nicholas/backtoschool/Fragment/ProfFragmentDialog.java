package com.example.nicholas.backtoschool.Fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicholas.backtoschool.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Christina on 12/9/2016.
 */

public class ProfFragmentDialog extends DialogFragment{

    Button cancel, save;
    EditText newPass, confPass;
    FirebaseDatabase fd;
    DatabaseReference db;
    FirebaseAuth fba;
    String userId = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View profView = inflater.inflate(R.layout.fragment_profile, container, false);
        getDialog().setTitle("Change Password");

        fd = FirebaseDatabase.getInstance();
        db = fd.getReference().child("Users");
        fba = FirebaseAuth.getInstance();

        userId = fba.getCurrentUser().getUid();

        Button cancel = (Button) profView.findViewById(R.id.dismiss);
        Button save = (Button) profView.findViewById(R.id.ok);
        newPass = (EditText) profView.findViewById(R.id.newPasstxt);
        confPass = (EditText) profView.findViewById(R.id.confPasstxt);

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

               if(newPass.getText().toString().equals("")){
                   Toast.makeText(view.getContext(), "New Password must be filled!", Toast.LENGTH_SHORT).show();
               }
               else if(confPass.getText().toString().equals("")){
                   Toast.makeText(view.getContext(), "Confirm Password must be filled!", Toast.LENGTH_SHORT).show();
               }
               else if(!newPass.getText().toString().equals(confPass.getText().toString())){
                   Toast.makeText(view.getContext(), "New Password and Confirm Password must be the same!", Toast.LENGTH_SHORT).show();
               }
               else{

                   fba.getCurrentUser().updatePassword(newPass.getText().toString());

                   Toast.makeText(view.getContext(), "Change passsword succeed!", Toast.LENGTH_SHORT).show();

                   dismiss();
               }

            }
        });

        return profView;
    }
}
