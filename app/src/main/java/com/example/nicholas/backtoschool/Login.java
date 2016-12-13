package com.example.nicholas.backtoschool;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Config;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicholas.backtoschool.FirebaseHelper.UserFirebaseHelper;
import com.example.nicholas.backtoschool.Model.User;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Login extends AppCompatActivity implements View.OnClickListener{

    EditText txtusername,txtpassword;
    Button btnLogin,gen;
    DatabaseReference db;
    UserFirebaseHelper ufh;
    ArrayList<User> usr;
    FirebaseAuth mfauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        //AppEventsLogger.activateApp(this);

        usr = new ArrayList<>();
        txtusername =(EditText)findViewById(R.id.txtloginUsername);
        txtpassword =(EditText)findViewById(R.id.txtloginPassword);
        btnLogin =(Button)findViewById(R.id.btnLogin);
        gen =(Button)findViewById(R.id.login_register);
        btnLogin.setOnClickListener(this);
        gen.setOnClickListener(this);
        db = FirebaseDatabase.getInstance().getReference();
        ufh = new UserFirebaseHelper(db);
        usr = ufh.retrieve();
        mfauth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==btnLogin.getId()){
            String email = txtusername.getText().toString();
            String password = txtpassword.getText().toString();

            email = email.trim();
            password = password.trim();
            if(email.isEmpty()|| password.isEmpty())
            Toast.makeText(Login.this, "Username or password must be filled", Toast.LENGTH_SHORT).show();
            else {

                btnLogin.setEnabled(false);
                mfauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MenuActivity.class);
                            startActivity(intent);

                        }
                btnLogin.setEnabled(true);
                    }
                });


            }
        }
        else if(view.getId()==gen.getId()){
            Intent intent = new Intent(Login.this,Register.class);
            startActivity(intent);
        }
    }

}
