package com.example.nicholas.backtoschool.DialogFragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicholas.backtoschool.FirebaseHelper.ClassFirebaseHelper;
import com.example.nicholas.backtoschool.Model.Reply;
import com.example.nicholas.backtoschool.Model.User;
import com.example.nicholas.backtoschool.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

/**
 * Created by Christina on 12/12/2016.
 */
public class AddReplyFragmentDialog extends DialogFragment {

    ClassFirebaseHelper cfh;
    FirebaseAuth fba;
    FirebaseDatabase fd;
    DatabaseReference dbClass;
    DatabaseReference dbUser;
    EditText replyId, replyContent;
    Button add, cancel;
    String fId = "", cId = "", rName = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View replyView = inflater.inflate(R.layout.fragment_add_reply, container, false);
        getDialog().setTitle("Add Reply");

        fba = FirebaseAuth.getInstance();
        fd = FirebaseDatabase.getInstance();
        dbClass = fd.getReference();
        dbUser = fd.getReference().child("Users");
        cfh = new ClassFirebaseHelper(dbClass);
        cfh.retrieve();

        replyContent = (EditText) replyView.findViewById(R.id.contenttxt);
        add = (Button) replyView.findViewById(R.id.add);
        cancel = (Button) replyView.findViewById(R.id.cancel);

        Bundle b = getActivity().getIntent().getExtras();
        cId = b.getString("classId");
        fId = b.getString("forumId");

        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    if (user.getUsername().equals(fba.getCurrentUser().getEmail())) {
                        rName = user.getName();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Reply reply = new Reply();
                reply.setRepliedby(rName);
                reply.setReplycontent(replyContent.getText().toString());
                reply.setReplyDate(new Date(System.currentTimeMillis()));
                cfh.replyforum(fId, cId, reply);

                //oast.makeText(replyView.getContext(), "ReplyID = "+replyId.getText().toString(), Toast.LENGTH_SHORT).show();

                dismiss();
            }
        });



        return replyView;
    }
}
