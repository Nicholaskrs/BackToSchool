package com.example.nicholas.backtoschool.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nicholas.backtoschool.Model.User;
import com.example.nicholas.backtoschool.R;

import java.util.ArrayList;

/**
 * Created by Nicholas on 12/12/2016.
 */

public class EditScoreAdapter extends BaseAdapter {
    ArrayList<User>users;
    Context c;

    public EditScoreAdapter(Context c, ArrayList<User>users) {
    this.c=c;
    this.users=users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(c).inflate(R.layout.add_score, viewGroup, false);
        }
        TextView name=(TextView)view.findViewById(R.id.username);
        EditText score=(EditText)view.findViewById(R.id.score);

        name.setText(users.get(i).getName());


        return view;
    }
}
