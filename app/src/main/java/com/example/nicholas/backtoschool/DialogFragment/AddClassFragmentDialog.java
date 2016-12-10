package com.example.nicholas.backtoschool.DialogFragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nicholas.backtoschool.R;

/**
 * Created by Christina on 12/9/2016.
 */

public class AddClassFragmentDialog extends DialogFragment {

    Button save, cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View classView = inflater.inflate(R.layout.fragment_addclass, container, false);
        getDialog().setTitle("Add Class");

        save = (Button) classView.findViewById(R.id.add);
        cancel = (Button) classView.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                dismiss();
            }
        });

        return classView;
    }
}
