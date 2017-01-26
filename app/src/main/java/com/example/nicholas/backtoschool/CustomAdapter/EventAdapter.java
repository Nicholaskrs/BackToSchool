package com.example.nicholas.backtoschool.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nicholas.backtoschool.R;

import java.util.List;

/**
 * Created by Nicholas on 1/26/2017.
 */

public class EventAdapter extends BaseAdapter {
    List<String> events;
    Context c;
   public EventAdapter(Context c,List<String>events)
    {
        this.c=c;
        this.events=events;
    }
    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(c).inflate(R.layout.fragment_event,viewGroup,false);
        }
        TextView event=(TextView) view.findViewById(R.id.event_name);



        final String cr=(String) this.getItem(i);
        System.out.println(cr+ "item no "+ i);
        event.setText(cr);

        return view;
    }
}
