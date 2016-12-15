package com.example.nicholas.backtoschool.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicholas.backtoschool.Model.ClassReminder;
import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.example.nicholas.backtoschool.Model.User;
import com.example.nicholas.backtoschool.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nicholas on 12/13/2016.
 */

public class AddActivityFragmentDialog extends DialogFragment {
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private Button btnSubmit;
    EditText activitytext;
    DatabaseReference db,dbu;
    String cId;
    ClassReminder cr;
    ArrayList<String>key=new ArrayList<>();
    public AddActivityFragmentDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db= FirebaseDatabase.getInstance().getReference().child("Class");
        dbu=FirebaseDatabase.getInstance().getReference().child("Users");
        View view = inflater.inflate(R.layout.fragment_add_activity, container, false);
        getDialog().setTitle("Add Activity");
        cId=getArguments().getString("ClassID");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                key.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ClassRoom classroom= snapshot.getValue(ClassRoom.class);
                        if(classroom.getClassRoomID().equals(cId)){

                            for(String uid:classroom.getUserID()){
                                key.add(uid);
                            }
                            break;
                        }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        activitytext=(EditText)view.findViewById(R.id.txtActivity);
        dateView = (TextView)view.findViewById(R.id.txtdeadline);
        btnSubmit=(Button)view.findViewById(R.id.btnSubmit);
        Date today=new Date(System.currentTimeMillis());
        year = today.getYear()+1900;
        month = today.getMonth();
        day = today.getDate();
        datePicker=(DatePicker) view.findViewById(R.id.datePicker);

        datePicker.init(year, month , day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                showDate(i,i1,i2);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cr=new ClassReminder();
                cr.setActivity(activitytext.getText().toString());
                long datetime=datePicker.getCalendarView().getDate();
                Date d=new Date(datetime);
                Date now=new Date(System.currentTimeMillis());
                cr.setDeadline(d);
                if(activitytext.getText().toString().equals("")){
                    Toast.makeText(view.getContext(), "Activity Must be filled", Toast.LENGTH_SHORT).show();
                }
                else if(now.after(d)){
                    Toast.makeText(view.getContext(), "You Can't make activity that date have already passed", Toast.LENGTH_SHORT).show();
                }
                else {
                    dbu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                if (key.contains(snapshot.getKey())) {

                                    User u = snapshot.getValue(User.class);
                                    u.addreminder(cr);
                                    dbu.child(snapshot.getKey()).setValue(u);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(view.getContext(), "Success", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });


        showDate(year, month+1, day);
        return view;
    }







    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}
