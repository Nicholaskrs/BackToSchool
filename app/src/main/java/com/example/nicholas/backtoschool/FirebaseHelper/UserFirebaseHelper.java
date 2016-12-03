package com.example.nicholas.backtoschool.FirebaseHelper;

import com.example.nicholas.backtoschool.Model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Nicholas on 12/3/2016.
 */
public class UserFirebaseHelper {
    DatabaseReference db;
    boolean saved;
    ArrayList<User> users=new ArrayList<User>();

    public boolean checkuser(String username,String password)
    {
        users=retrieve();
        for(int i=0;i<users.size();i++){
            if(users.get(i).getUsername().equals(username)&&users.get(i).getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    public boolean uservalid(String username){
        users=retrieve();
        for(int i=0;i<users.size();i++){
            if(users.get(i).getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }
    public UserFirebaseHelper(DatabaseReference db){
        this.db=db;
    }

    public boolean savedata(User Users) {
        if (Users == null)
            saved= false;
        else {
            try {
                db.child("Users").push().setValue(Users);
                saved = true;
            } catch (Exception e) {
                saved= false;
            }
        }
        return saved;
    }

    public void fenchData(DataSnapshot dataSnapshot){
        users.clear();

        for (DataSnapshot u :dataSnapshot.getChildren()) {
            User tempuser=u.getValue(User.class);
            users.add(tempuser);
        }
    }
    public ArrayList<User> retrieve(){
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fenchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fenchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return users;
    }
}
