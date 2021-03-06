package com.example.nicholas.backtoschool;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth fba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fba=FirebaseAuth.getInstance();
        TextView title = (TextView)findViewById(R.id.title);
        TextView power = (TextView) findViewById(R.id.powered);
        TextView nk = (TextView) findViewById(R.id.nk);
        TextView cx = (TextView) findViewById(R.id.cx);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/SqueakyChalkSound.ttf");
        title.setTypeface(custom_font);
        power.setTypeface(custom_font);
        nk.setTypeface(custom_font);
        cx.setTypeface(custom_font);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(fba.getCurrentUser()!=null){
                    Intent intent=new Intent(MainActivity.this,MenuActivity.class);
                    startActivity(intent);
                }
                else {
                    final Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                }finish();
            }
        }, 3000);


    }

}
