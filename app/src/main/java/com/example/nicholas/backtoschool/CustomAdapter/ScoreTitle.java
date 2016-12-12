package com.example.nicholas.backtoschool.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nicholas.backtoschool.Model.Score;
import com.example.nicholas.backtoschool.R;

import java.util.ArrayList;

/**
 * Created by Nicholas on 12/12/2016.
 */

public class ScoreTitle extends BaseAdapter {

    Context c;
    ArrayList<Score>scores=new ArrayList<>();

    public ScoreTitle(Context c, ArrayList<Score> scores) {
        this.c = c;
        this.scores = scores;
    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public Object getItem(int i) {
        return scores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(c).inflate(R.layout.score_title, viewGroup, false);
        }
        TextView name=(TextView)view.findViewById(R.id.scoretitle);
        name.setText(scores.get(i).getType());
        return view;
    }
}
