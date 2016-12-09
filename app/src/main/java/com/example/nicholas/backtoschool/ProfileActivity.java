package com.example.nicholas.backtoschool;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicholas.backtoschool.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.PipedOutputStream;

public class ProfileActivity extends AppCompatActivity {

    FirebaseDatabase fd;
    DatabaseReference db;
    FirebaseAuth fba;
    TextView nametxt, emailtxt, passtxt, agetxt, gendertxt, edutxt;
    Button changeBtn, saveBtn;
    String email = "", userId = "";
    String oldName = "", oldEmail = "", oldGender = "", oldEdu = "";
    int oldAge = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fd = FirebaseDatabase.getInstance();

        db = fd.getReference().child("Users");
        fba = FirebaseAuth.getInstance();
        nametxt = (TextView) findViewById(R.id.nametxt);
        emailtxt = (TextView) findViewById(R.id.emailtxt);
        passtxt = (TextView) findViewById(R.id.passtxt);
        agetxt = (TextView) findViewById(R.id.agetxt);
        gendertxt = (TextView) findViewById(R.id.gendertxt);
        edutxt = (TextView) findViewById(R.id.edutxt);
        changeBtn = (Button) findViewById(R.id.changebtn);
        saveBtn = (Button) findViewById(R.id.savebtn);

        nametxt.setEnabled(false);
        emailtxt.setEnabled(false);
        passtxt.setEnabled(false);
        agetxt.setEnabled(false);
        gendertxt.setEnabled(false);
        edutxt.setEnabled(false);
        saveBtn.setEnabled(false);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(changeBtn.getText().equals("Change Profile")){
                    nametxt.setEnabled(true);
                    emailtxt.setEnabled(true);
                    passtxt.setEnabled(true);
                    agetxt.setEnabled(true);
                    gendertxt.setEnabled(true);
                    edutxt.setEnabled(true);
                    saveBtn.setEnabled(true);
                    changeBtn.setText("Cancel");
                }
                else if(changeBtn.getText().equals("Cancel")) {
                    nametxt.setEnabled(false);
                    emailtxt.setEnabled(false);
                    passtxt.setEnabled(false);
                    agetxt.setEnabled(false);
                    gendertxt.setEnabled(false);
                    edutxt.setEnabled(false);
                    saveBtn.setEnabled(false);
                    changeBtn.setText("Change Profile");

                    //TODO UNIV BELOM MASUK



                }
            }
        });

        userId = fba.getCurrentUser().getUid();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nametxt.getText().equals("")){
                    Toast.makeText(getApplicationContext(), "Name must be filled!", Toast.LENGTH_SHORT).show();
                }
                else if(emailtxt.getText().equals("")){
                    Toast.makeText(getApplicationContext(), "Email must be filled!", Toast.LENGTH_SHORT).show();
                }
                else if(passtxt.getText().equals("")){
                    Toast.makeText(getApplicationContext(), "Password must be filled!", Toast.LENGTH_SHORT).show();
                }
                else if(agetxt.getText().equals("")){
                    Toast.makeText(getApplicationContext(), "Age must be filled!", Toast.LENGTH_SHORT).show();
                }
                else if(gendertxt.getText().equals("")){
                    Toast.makeText(getApplicationContext(), "Gender must be filled!", Toast.LENGTH_SHORT).show();
                }
                else if(edutxt.getText().equals("")){
                    Toast.makeText(getApplicationContext(), "Education level must be filled!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Change Profile succeed!", Toast.LENGTH_SHORT).show();
                    //update

                    db.child(userId).child("name").setValue(nametxt.getText().toString());
                    db.child(userId).child("username").setValue(emailtxt.getText().toString());
                    db.child(userId).child("age").setValue(Integer.parseInt(agetxt.getText().toString()));
                    db.child(userId).child("gender").setValue(gendertxt.getText().toString());
                    db.child(userId).child("educationalLevel").setValue(edutxt.getText().toString());

                    nametxt.setEnabled(false);
                    emailtxt.setEnabled(false);
                    passtxt.setEnabled(false);
                    agetxt.setEnabled(false);
                    gendertxt.setEnabled(false);
                    edutxt.setEnabled(false);
                    saveBtn.setEnabled(false);
                    changeBtn.setText("Change Profile");
                }
            }
        });


        email = fba.getCurrentUser().getEmail();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    if(user.getUsername().equals(email)){
                        nametxt.setText(user.getName());
                        emailtxt.setText(user.getUsername());
                        //passtxt.setText();
                        agetxt.setText(user.getAge()+"");
                        gendertxt.setText(user.getGender());
                        edutxt.setText(user.getEducationalLevel());

                        oldName = user.getName();
                        oldEmail = user.getUsername();
                        oldAge = user.getAge();
                        oldGender = user.getGender();
                        oldEdu = user.getEducationalLevel();

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
