package com.example.nicholas.backtoschool.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nicholas.backtoschool.Model.Forum;
import com.example.nicholas.backtoschool.R;

import java.util.ArrayList;

/**
 * Created by Christina on 12/12/2016.
 */
public class ForumAdapter extends BaseAdapter {

    Context c;
    ArrayList<Forum> forums;

    public ForumAdapter(Context c, ArrayList<Forum> forums){
        this.c = c;
        this.forums = forums;
    }

    @Override
    public int getCount() {
        return forums.size();
    }

    @Override
    public Object getItem(int i) {
        return forums.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = LayoutInflater.from(c).inflate(R.layout.forumlist, viewGroup, false);
        }

        TextView title = (TextView) view.findViewById(R.id.fTitle);
        TextView madeBy = (TextView) view.findViewById(R.id.madebytxt);

        Forum forum = (Forum) this.getItem(i);
        title.setText(forum.getForumTopic());
        madeBy.setText(forum.getMadeby());

        return view;
    }


}
