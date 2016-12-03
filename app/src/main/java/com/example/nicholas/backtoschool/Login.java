package com.example.nicholas.backtoschool;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Config;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicholas.backtoschool.FirebaseHelper.UserFirebaseHelper;
import com.example.nicholas.backtoschool.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Login extends AppCompatActivity implements View.OnClickListener{

    EditText txtusername,txtpassword;
    Button btnLogin,gen;
    DatabaseReference db;
    UserFirebaseHelper ufh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtusername=(EditText)findViewById(R.id.txtloginUsername);
        txtpassword=(EditText)findViewById(R.id.txtloginPassword);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        gen=(Button)findViewById(R.id.generate);
        btnLogin.setOnClickListener(this);
        gen.setOnClickListener(this);
        db = FirebaseDatabase.getInstance().getReference();
        ufh=new UserFirebaseHelper(db);
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

                if(ufh.checkuser(email,password))
                    Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Login.this, "Username or Password Wrong", Toast.LENGTH_SHORT).show();


            }
        }
        else if(view.getId()==gen.getId()){
            Intent intent = new Intent(Login.this,Register.class);
            startActivity(intent);
            finish();

        }
    }
}
