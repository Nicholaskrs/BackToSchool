package com.example.nicholas.backtoschool.CustomAdapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nicholas.backtoschool.Model.User;
import com.example.nicholas.backtoschool.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Nicholas on 12/3/2016.
 */
public class UserAdapter extends BaseAdapter{
    Context c;
    ArrayList<User> users;

    public UserAdapter(Context c,ArrayList<User> users)
    {
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


    public long getItemId(Object i) {
        return users.indexOf(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = LayoutInflater.from(c).inflate(R.layout.userslist, viewGroup, false);
        }

        TextView email = (TextView) view.findViewById(R.id.emailtxt);
        TextView name = (TextView) view.findViewById(R.id.nametxt);
        TextView age = (TextView) view.findViewById(R.id.agetxt);

        User user = (User) this.getItem(i);
        email.setText((i+1)+"");
        name.setText(user.getName());
        age.setText("");

        return view;
    }
}
