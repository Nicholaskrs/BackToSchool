package com.example.nicholas.backtoschool.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nicholas.backtoschool.Model.ClassRoom;
import com.example.nicholas.backtoschool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicholas on 12/11/2016.
 */

public class ClassListAdapter extends BaseAdapter {

    List<ClassRoom> classRooms;
    Context c;

    public ClassListAdapter(Context c,List<ClassRoom>classRooms){
        this.c=c;
        this.classRooms=classRooms;
    }
    @Override
    public int getCount() {
        return classRooms.size();
    }

    @Override
    public Object getItem(int i) {
        return classRooms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(c).inflate(R.layout.classlistview,viewGroup,false);
        }
        TextView id=(TextView)view.findViewById(R.id.classID);
        TextView name=(TextView)view.findViewById(R.id.className);

        final ClassRoom cr=(ClassRoom) this.getItem(i);

        id.setText(cr.getClassRoomID());
        name.setText(cr.getClassName());


        return view;
    }
}
