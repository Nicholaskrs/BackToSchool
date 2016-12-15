package com.example.nicholas.backtoschool;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nicholas.backtoschool.Model.ClassReminder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Vector;

/**
 * Created by Nicholas on 12/6/2016.
 */
public class CalendarView extends LinearLayout
{
    // internal components
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private EventHandler eventHandler;
    private TextView txtDate;
    private GridView grid;
    private Calendar currentDate = Calendar.getInstance();
    private ArrayList<ClassReminder> classremind=new ArrayList<>();
    HashSet<ClassReminder> events=new HashSet<>();
    public CalendarView(Context context)
    {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initControl(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }


    private void initControl(Context context,AttributeSet attrs)
    {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.control_calendar, this);

        header = (LinearLayout)findViewById(R.id.calendar_header);
        header.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.winter));
        btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView)findViewById(R.id.calendar_next_button);
        txtDate = (TextView)findViewById(R.id.calendar_date_display);
        grid = (GridView)findViewById(R.id.calendar_grid);
        btnNext.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar(events);
            }
        });

        // subtract one month and refresh UI
        btnPrev.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar(events);
            }
        });
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id)
            {
                if (eventHandler == null)
                    return false;

                Date date = (Date)view.getItemAtPosition(position);

                eventHandler.onDayLongPress(date);

                return true;
            }
        });
    }
    public void updateCalendar()
    {
        updateCalendar(null);
    }
    public void updateCalendar(HashSet<ClassReminder> events)
    {
    ArrayList<Date> cells = new ArrayList<>();
        this.events=events;
    Calendar calendar = (Calendar)currentDate.clone();

    // determine the cell for current month's beginning
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

    // move calendar backwards to the beginning of the week
    calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

    // fill cells (42 days calendar as per our business logic)
    while (cells.size() < 42)
    {
        cells.add(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
    }

    // update grid
        grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

    // update title
    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
    txtDate.setText(sdf.format(currentDate.getTime()));
    }
    private class CalendarAdapter extends ArrayAdapter<Date>
    {
        private HashSet<ClassReminder> eventDays;

        // for view inflation
        private LayoutInflater inflater;
        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<ClassReminder> eventDays) {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();

            // today
            Date today = new Date();

            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);



            // if this day has an event, specify event image
            view.setBackgroundResource(0);
            if (eventDays != null)
            {
                classremind.clear();
                for (ClassReminder eventDate : eventDays)
                {
                    classremind.add(eventDate);
                     Date date1= eventDate.getDeadline();
                        if (date1.getDate() == date.getDate() &&
                                date1.getMonth() == date.getMonth() &&
                                date1.getYear() == date.getYear())
                        {
                            // mark this day for event
                            view.setBackgroundResource(R.drawable.reminder);
                            break;
                        }
                }
            }

            // clear styling
            ((TextView)view).setTypeface(null, Typeface.NORMAL);
            ((TextView)view).setTextColor(Color.BLACK);

            if (month != today.getMonth() || year != today.getYear())
            {
                // if this day is outside current month, grey it out
                ((TextView)view).setTextColor(getResources().getColor(R.color.greyed_out));
            }
            else if (day == today.getDate())
            {
                // if it is today, set it to blue/bold
                ((TextView)view).setTypeface(null, Typeface.BOLD);
                ((TextView)view).setTextColor(getResources().getColor(R.color.today));
            }

            // set text
            ((TextView)view).setText(String.valueOf(date.getDate()));

            return view;
        }

    }
    public void setEventHandler(EventHandler eventHandler)
    {
        this.eventHandler = eventHandler;
    }

    public interface EventHandler
    {
        void onDayLongPress(Date date);

    }
}

