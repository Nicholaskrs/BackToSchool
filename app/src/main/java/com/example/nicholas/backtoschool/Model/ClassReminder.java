package com.example.nicholas.backtoschool.Model;

import java.util.Date;

/**
 * Created by Nicholas on 12/9/2016.
 */

public class ClassReminder {
    String activity;
    Date deadline;

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
