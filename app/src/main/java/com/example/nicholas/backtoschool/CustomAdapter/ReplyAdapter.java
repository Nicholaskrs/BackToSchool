package com.example.nicholas.backtoschool.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicholas.backtoschool.Model.Reply;
import com.example.nicholas.backtoschool.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Christina on 12/12/2016.
 */
public class ReplyAdapter extends BaseAdapter {

    Context c;
    ArrayList<Reply> replies;
    String rDate = "";

    public ReplyAdapter(Context c, ArrayList<Reply> replies){
        this.c = c;
        this.replies = replies;
    }

    @Override
    public int getCount() {
        return replies.size();
    }

    @Override
    public Object getItem(int i) {
        return replies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = LayoutInflater.from(c).inflate(R.layout.forumreplies, viewGroup, false);
        }

        TextView madeBy = (TextView) view.findViewById(R.id.madebytxt);
        TextView rdate = (TextView) view.findViewById(R.id.rDate);
        TextView content = (TextView) view.findViewById(R.id.contenttxt);

        Reply reply = (Reply) this.getItem(i);

        DateFormat df = SimpleDateFormat.getDateInstance();
        rDate = df.format(reply.getReplyDate());

        content.setText(reply.getReplycontent());
        rdate.setText(rDate);
        madeBy.setText(reply.getRepliedby());

        return view;
    }
}
