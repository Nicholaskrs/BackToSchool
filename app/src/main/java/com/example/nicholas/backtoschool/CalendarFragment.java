package com.example.nicholas.backtoschool;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;


public class CalendarFragment extends Fragment {

    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private CalendarView.EventHandler eventHandler;
    private TextView txtDate;
    private GridView grid;
    private Calendar currentDate = Calendar.getInstance();

    public CalendarFragment() {
        // Required empty public constructor
        /**/


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        CalendarView cv = (CalendarView)view.findViewById(R.id.calendar_view);
        cv.updateCalendar(events);
        Toast.makeText(getContext(), "Called", Toast.LENGTH_SHORT).show();
        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date) {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(getContext(), df.format(date), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}
