package com.example.nicholas.backtoschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nicholas.backtoschool.CustomAdapter.UserAdapter;
import com.example.nicholas.backtoschool.FirebaseHelper.UserFirebaseHelper;
import com.example.nicholas.backtoschool.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity implements View.OnClickListener{

    Spinner grade;
    EditText txtusername,txtpassword,txtcpassword,txtname,txtage,txtschool,txtStudentID;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    Button btnRegister;
    UserFirebaseHelper ufh;
    DatabaseReference db;
    ArrayList<User> usr;
    FirebaseAuth mfauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        grade=(Spinner)findViewById(R.id.cmbLevel);
        usr=new ArrayList<>();
        btnRegister=(Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        db = FirebaseDatabase.getInstance().getReference();
        ufh=new UserFirebaseHelper(db);
        List<String> grades=new ArrayList<String>();
        grades.add("Elementary School");
        grades.add("Junior High School");
        grades.add("Senior High School");
        grades.add("University");

        ArrayAdapter<String> gradesadapter=new ArrayAdapter<String>(this,R.layout.spinner_item,grades);
        gradesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grade.setAdapter(gradesadapter);
        txtusername=(EditText) findViewById(R.id.txtUsername);
        txtpassword=(EditText)findViewById(R.id.txtPassword);
        txtcpassword=(EditText)findViewById(R.id.txtcPassword);
        txtname=(EditText)findViewById(R.id.txtName);
        txtage=(EditText)findViewById(R.id.txtAge);
        txtschool=(EditText)findViewById(R.id.txtschool);
        radioGroup=(RadioGroup)findViewById(R.id.rbggender);
        usr=ufh.retrieve();
        mfauth=FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        String email=txtusername.getText().toString();
        if(txtname.getText().toString().trim().equals("")){
            Toast.makeText(Register.this, "Name must be filled", Toast.LENGTH_SHORT).show();
        }else if(txtusername.getText().toString().trim().equals("")){
            Toast.makeText(Register.this, "Email must be filled", Toast.LENGTH_SHORT).show();
        }else if(!email.contains("@")|| !email.contains(".")|| email.indexOf("@")!=email.lastIndexOf("@") || email.lastIndexOf("@")>email.lastIndexOf(".")||
                email.indexOf(".")==0|| email.indexOf("@")==email.length()-1 || email.indexOf(".")==email.length()-1||email.indexOf("@")==0){
            Toast.makeText(Register.this, "Email Format not match", Toast.LENGTH_SHORT).show();
        }else if(txtpassword.getText().toString().length()<=6){
            Toast.makeText(Register.this, "Password must more than 6 character", Toast.LENGTH_SHORT).show();
        }else if(!txtpassword.getText().toString().equals(txtcpassword.getText().toString())){
            Toast.makeText(Register.this, "Password and Confirm Password not match", Toast.LENGTH_SHORT).show();
        }else if(txtschool.getText().toString().trim().equals("")){
            Toast.makeText(Register.this, "School must be filled", Toast.LENGTH_SHORT).show();
        }else if(Integer.parseInt(txtage.getText().toString())<=0){
            Toast.makeText(Register.this, "Age Must More than 0", Toast.LENGTH_SHORT).show();
        }else if(radioGroup.getCheckedRadioButtonId()==-1){
            Toast.makeText(Register.this, "Gender must be selected", Toast.LENGTH_SHORT).show();
        }else if(grade.getSelectedItem()==null){
            Toast.makeText(Register.this, "Education Level must be selected", Toast.LENGTH_SHORT).show();
        }else if(!uservalid(txtusername.getText().toString().trim())){
            Toast.makeText(Register.this, "Username is already taken please take another username", Toast.LENGTH_SHORT).show();

        }
        else {


//TODO hapus student id gak kepake
            if(mfauth.getCurrentUser()!=null)
                mfauth.signOut();
                mfauth.createUserWithEmailAndPassword(txtusername.getText().toString(), txtpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    mfauth.signInWithEmailAndPassword(txtusername.getText().toString(),txtpassword.getText().toString());
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    if(mfauth.getCurrentUser()!=null) {
                        radioButton = (RadioButton) findViewById(selectedId);
                        String id=mfauth.getCurrentUser().getUid();
                        User u = new User();
                        u.setAge(Integer.parseInt(txtage.getText().toString().trim()));
                        u.setEducationalLevel(grade.getSelectedItem().toString().trim());
                        u.setName(txtname.getText().toString().trim());
                        u.setSchool(txtschool.getText().toString().trim());
                        u.setUsername(txtusername.getText().toString().trim());
                        u.setGender(radioButton.getText().toString().trim());
                        boolean check=ufh.savedata(u, id);
                        Toast.makeText(Register.this, "Save data"+check, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Register.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(Register.this, "Register Failed!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }

    }
    private boolean uservalid(String username){

        for(int i=0;i<usr.size();i++){
            if(usr.get(i).getUsername().equals(username))
                return false;
        }

        return true;
    }
}
