package com.example.nicholas.backtoschool;



import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.telecom.Call;
import android.util.Config;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicholas.backtoschool.FirebaseHelper.UserFirebaseHelper;
import com.example.nicholas.backtoschool.Model.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class Login extends AppCompatActivity implements View.OnClickListener{

    EditText txtusername,txtpassword;
    Button btnLogin, gen;
    LoginButton loginBtn;
    DatabaseReference db;
    UserFirebaseHelper ufh;
    ArrayList<User> usr;
    FirebaseAuth mfauth;
    CallbackManager cbm;
    FirebaseAuth.AuthStateListener fbalistener;
    String TAG = "";
    String email = "", birth = "", name = "",gender="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_login);

        cbm = CallbackManager.Factory.create();

        usr = new ArrayList<>();
        txtusername =(EditText)findViewById(R.id.txtloginUsername);
        txtpassword =(EditText)findViewById(R.id.txtloginPassword);
        btnLogin =(Button)findViewById(R.id.btnLogin);
        gen =(Button)findViewById(R.id.login_register);
        mfauth = FirebaseAuth.getInstance();

        fbalistener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser fuser = firebaseAuth.getCurrentUser();
                if(fuser != null){
                    Log.d(TAG, "onAuthStateChanged: signed_in: "+fuser.getUid());
                }
                else{
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
            }
        };

        loginBtn = (LoginButton) findViewById(R.id.login_button);
        loginBtn.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));

        LoginManager.getInstance().registerCallback(cbm, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //handleFacebookAccessToken(loginResult.getAccessToken());
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                Toast.makeText(getApplicationContext(), "Facebook login succeed: " + loginResult.getAccessToken(), Toast.LENGTH_SHORT).show();

                System.out.println("or Here?");
                GraphRequest req = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback(){
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("Login", response.toString());
                        try {
                            email = object.getString("email");
                            birth = object.getString("birthday");
                            name = object.getString("name");
                            gender= object.getString("gender");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                });
                Bundle params = new Bundle();
                params.putString("fields", "id,name,email,gender,birthday");
                req.setParameters(params);
                req.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Facebook login cancelled!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Facebook login error!", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogin.setOnClickListener(this);
        gen.setOnClickListener(this);
        db = FirebaseDatabase.getInstance().getReference();
        ufh = new UserFirebaseHelper(db);
        usr = ufh.retrieve();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cbm.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mfauth.addAuthStateListener(fbalistener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(fbalistener != null){
            mfauth.removeAuthStateListener(fbalistener);
        }
    }

    private void handleFacebookAccessToken(AccessToken token){
        Log.d(TAG, "handleFacebookAccessToken: "+token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mfauth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {

                if(!task.isSuccessful()){

                    Log.w(TAG, "signInWithCredential", task.getException());
                    Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    System.out.println(email+ " asdasd");
                    int year = Integer.parseInt(birth.substring(6));

                    Calendar cal = Calendar.getInstance();
                    int currYear = cal.get(Calendar.YEAR);

                    int age = currYear - year;

                    User user = new User();
                    user.setUsername(email);
                    user.setAge(age);
                    user.setName(name);
                    user.setGender(gender);
                    user.setSchool("");
                    user.setEducationalLevel("");
                    ufh.savedata(user, mfauth.getCurrentUser().getUid());

                    Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MenuActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
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
            finish();
        }
    }


}
