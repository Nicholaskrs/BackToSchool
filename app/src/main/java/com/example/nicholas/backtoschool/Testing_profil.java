package com.example.nicholas.backtoschool;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Testing_profil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_profil);
        FirebaseAuth fauth=FirebaseAuth.getInstance();
        if(fauth.getCurrentUser()!=null) {
            TextView t = (TextView) findViewById(R.id.testing);
            t.setText(fauth.getCurrentUser().getUid());
            TextView t2 = (TextView) findViewById(R.id.testing2);
            t2.setText(fauth.getCurrentUser().getEmail());
        }
        else
        {
            TextView t = (TextView) findViewById(R.id.testing);
            t.setText("Fail Dude");
        }
    }
}
