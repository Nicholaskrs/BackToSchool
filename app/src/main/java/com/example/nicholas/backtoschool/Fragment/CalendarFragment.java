package com.example.nicholas.backtoschool.Fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicholas.backtoschool.CalendarView;
import com.example.nicholas.backtoschool.Model.ClassReminder;
import com.example.nicholas.backtoschool.Model.User;
import com.example.nicholas.backtoschool.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;


public class CalendarFragment extends Fragment {

    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    DatabaseReference db;
    FirebaseAuth fba;
    private CalendarView.EventHandler eventHandler;
    private TextView txtDate;
    private GridView grid;
    CalendarView cv;
    private Calendar currentDate = Calendar.getInstance();
    HashSet<Date> events = new HashSet<>();
    HashSet<ClassReminder> classReminders=new HashSet<>();
    NotificationManager mNotifyMgr;
    android.support.v4.app.NotificationCompat.Builder mBuilder;
    public CalendarFragment() {
        // Required empty public constructor
        /**/


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        db=FirebaseDatabase.getInstance().getReference().child("Users");
        fba=FirebaseAuth.getInstance();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                classReminders.clear();
                Date today=new Date(System.currentTimeMillis());
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.getKey().equals(fba.getCurrentUser().getUid())){
                        User u=ds.getValue(User.class);

                        for(ClassReminder cr:u.getSchedule()){

                            classReminders.add(cr);
                            if(cr.getDeadline().getDate()==today.getDate()+1&&
                                    cr.getDeadline().getMonth()==today.getMonth()&&
                                    cr.getDeadline().getYear()==today.getYear()) {
                                mBuilder.setContentText("Tomorrow Deadline: "+cr.getActivity());
                                mNotifyMgr.notify(1, mBuilder.build());
                            }

                        }
                    }
                }
                cv.updateCalendar(classReminders);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        cv = (CalendarView)view.findViewById(R.id.calendar_view);

        mNotifyMgr =(NotificationManager) view.getContext().getSystemService(view.getContext().NOTIFICATION_SERVICE);
        mBuilder =new NotificationCompat.Builder(view.getContext()).setSmallIcon(R.drawable.profile).setContentTitle("My notification");

// Builds the notification and issues it.



        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date) {
                boolean find=false;
                // show returned day
                for(ClassReminder reminder:classReminders){
                    if(reminder.getDeadline().getDate()==date.getDate() &&
                            reminder.getDeadline().getMonth()==date.getMonth() &&
                            reminder.getDeadline().getYear()==date.getYear() )
                    {
                        Toast.makeText(getContext(), reminder.getActivity(), Toast.LENGTH_SHORT).show();
                        find=true;
                    }
                }
                if(!find) {
                    DateFormat df = SimpleDateFormat.getDateInstance();
                    Toast.makeText(getContext(), df.format(date), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


}
