package com.example.nicholas.backtoschool.FirebaseHelper;

import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.example.nicholas.backtoschool.Model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Nicholas on 12/9/2016.
 */

public class ClassFirebaseHelper {
    DatabaseReference db;
    boolean saved;
    ArrayList<ClassRoom> classRooms=new ArrayList<ClassRoom>();



    public ClassFirebaseHelper(DatabaseReference db){
        this.db=db;
    }

    public boolean savedata(ClassRoom classRoom,String uid) {
        if (classRoom == null)
            return false;
        else {
            try {
                db.child("Class").push().setValue(classRoom);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
    public String adduser(String classRoomID,String userid,User user){
        if(classRooms.isEmpty())
            retrieve();
        boolean find=false;
        int classindex=0;
        for(int i=0;i<classRooms.size();i++){
            if(classRooms.get(i).getClassRoomID().equals(classRoomID))
                find=true;
        }
        if(find)
        {
            ClassRoom tempclass=classRooms.get(classindex);
            Vector<String> u=tempclass.getUserID();
            if(u.contains(userid))
                return "You already in the class";
            u.add(userid);
            user.addclassroom(tempclass);
            return "Class successfuly added";
        }
        return "ClassID not found";
    }



    public void fenchData(DataSnapshot dataSnapshot){
        classRooms.clear();

        for (DataSnapshot u :dataSnapshot.getChildren()) {
            ClassRoom temp=u.getValue(ClassRoom.class);
            classRooms.add(temp);
        }
    }
    public ArrayList<ClassRoom> retrieve(){
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
        return classRooms;
    }
}
