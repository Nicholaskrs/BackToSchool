package com.example.nicholas.backtoschool.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nicholas.backtoschool.Model.StudentScore;
import com.example.nicholas.backtoschool.R;

import java.util.ArrayList;

/**
 * Created by Nicholas on 12/12/2016.
 */

public class ScoreAdapter extends BaseAdapter{
    Context c;
    ArrayList<StudentScore> studentScores;
    ArrayList<String> types;
    public ScoreAdapter(Context c,ArrayList<StudentScore> studentScores,ArrayList<String>types)
    {
        this.c=c;
        this.types=types;
        this.studentScores=studentScores;
    }

    @Override
    public int getCount() {
        return studentScores.size();
    }

    @Override
    public Object getItem(int i) {
        return studentScores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(c).inflate(R.layout.myscoreview, viewGroup, false);
        }
        System.out.println("kepanggil?");
        TextView name=(TextView)view.findViewById(R.id.MyName);
        TextView score=(TextView)view.findViewById(R.id.MyScore);
        TextView type=(TextView)view.findViewById(R.id.Type);

        StudentScore tempscore=studentScores.get(i);
        name.setText(tempscore.getStudentName());
        score.setText(tempscore.getScore()+"");
        type.setText(types.get(i));

        return view;
    }
}
