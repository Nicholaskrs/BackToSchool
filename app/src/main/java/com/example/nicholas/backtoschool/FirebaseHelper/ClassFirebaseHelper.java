package com.example.nicholas.backtoschool.FirebaseHelper;

import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.example.nicholas.backtoschool.Model.Forum;
import com.example.nicholas.backtoschool.Model.Reply;
import com.example.nicholas.backtoschool.Model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
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

    public String addclassroom(ClassRoom classRoom) {
        if (classRoom == null)
            return "Classroom is null";

            try {
                db.child("Class").child(classRoom.getClassRoomID()).setValue(classRoom);
                return "Success";
            } catch (Exception e) {
                return e.toString();
            }
    }

    public String adduser(String classRoomID,String userid,User user){
        if(classRooms.isEmpty())
            retrieve();
        boolean find=false;
        int classindex=0;
        for(int i=0;i<classRooms.size();i++){
            if(classRooms.get(i).getClassRoomID().equals(classRoomID)) {
                find = true;
                classindex=i;
            }
        }
        if(find)
        {
            ClassRoom tempclass=classRooms.get(classindex);
            List<String> u=tempclass.getUserID();
            if(u.contains(userid))
                return "You already in the class";
            tempclass.addUser(user);
            tempclass.adduserid(userid);
            db.child("Class").child(classRoomID).setValue(tempclass);
            return "User successfully added";
        }
        return "ClassID not found";
    }
    public String addnewforum(Forum forum, String ClassID)
    {
        if(forum==null)
            return "Forum Value is null";
        if(classRooms.isEmpty())
            retrieve();
        boolean find=false;
        int classindex=0;
        for(int i=0;i<classRooms.size();i++){
            if(classRooms.get(i).getClassRoomID().equals(ClassID)) {
                find = true;
                classindex=i;
            }
        }
        if(find)
        {
            ClassRoom tempclass=classRooms.get(classindex);
            try {
                tempclass.addforum(forum);
                db.child("Class").child(ClassID).setValue(tempclass);
                return "Forum added Successfully";
            } catch (Exception e) {
                return "Firebase Error";
            }

        }
        return "Class not found";

    }

    public String replyforum(String forumid, String ClassID, Reply reply)
    {
        if(reply==null)
            return "Forum Value is null";
        if(classRooms.isEmpty())
            retrieve();
        boolean find=false;
        int classindex=0;
        for(int i=0;i<classRooms.size();i++){
            if(classRooms.get(i).getClassRoomID().equals(ClassID)) {
                find = true;
                classindex=i;
                break;
            }
        }
        if(find)
        {
            ClassRoom tempclass=classRooms.get(classindex);
            List<Forum>tempforums=tempclass.getForums();
            int forumindex=-1;
            for(int i=0;i<tempforums.size();i++){
                if(tempforums.get(i).getForumID().equals(forumid)) {
                    forumindex = i;
                    break;
                }
            }

            if(forumindex==-1)
                return "Error Forum not found";
            Forum tempforum=tempforums.get(forumindex);
            tempforum.addReplies(reply);
            tempforums.set(forumindex,tempforum);
            try {
                tempclass.setForums(tempforums);
                db.child("Class").child(ClassID).setValue(tempclass);
                return "Replies added successfully";
            } catch (Exception e) {
                return "Firebase Error";
            }

        }
        return "Class not found";

    }



    public void fenchData(DataSnapshot dataSnapshot){
        classRooms.clear();

        for (DataSnapshot u :dataSnapshot.getChildren()) {

            ClassRoom temp=u.getValue(ClassRoom.class);

            classRooms.add(temp);
            System.out.println(classRooms.size());

        }
    }
    public ArrayList<ClassRoom> retrieve(){
        db.child("Class").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fenchData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
        return classRooms;
    }
    public ArrayList<ClassRoom> getClassRooms(){
        return classRooms;
    }
}
