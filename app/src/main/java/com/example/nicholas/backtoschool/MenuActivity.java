package com.example.nicholas.backtoschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.nicholas.backtoschool.CustomAdapter.ViewPagerAdapter;
import com.example.nicholas.backtoschool.DialogFragment.AddClassFragmentDialog;
import com.example.nicholas.backtoschool.DialogFragment.MakeClassFragmentDialog;
import com.example.nicholas.backtoschool.Fragment.CalendarFragment;
import com.example.nicholas.backtoschool.Fragment.ClassList;
import com.example.nicholas.backtoschool.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtname,txtemail;
    DatabaseReference db;
    FirebaseAuth mfauth;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mfauth=FirebaseAuth.getInstance();


        //mPostReference.addValueEventListener(postListener);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter vpadapter = new ViewPagerAdapter(getSupportFragmentManager());

        vpadapter.add(new CalendarFragment(),"Calendar");
        vpadapter.add(new ClassList(),"My Class");
        viewPager.setAdapter(vpadapter);
        tabLayout.setupWithViewPager(viewPager);




    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        txtname=(TextView)findViewById(R.id.nav_nametext);
        txtemail=(TextView)findViewById(R.id.textView);
        db = FirebaseDatabase.getInstance().getReference().child("Users");
        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot user: dataSnapshot.getChildren()) {
                    //this is all you need to get a specific user by Uid

                    if (user.getKey().equals(mfauth.getCurrentUser().getUid())) {
                        User a = user.getValue(User.class);
                        txtname.setText(a.getName());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //txtemail.setText("asdasd");
        txtemail.setText(mfauth.getCurrentUser().getEmail());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent i=new Intent(MenuActivity.this, ProfileActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_addClass) {
            android.app.FragmentManager fm = getFragmentManager();
            AddClassFragmentDialog afd = new AddClassFragmentDialog();
            afd.show(fm, "Add Class");
        }
        else if (id == R.id.nav_makeClass) {
            android.app.FragmentManager fm = getFragmentManager();
            MakeClassFragmentDialog afd = new MakeClassFragmentDialog();
            afd.show(fm, "Make Class");
        }
        else if (id == R.id.nav_logout) {
            Intent intent = new Intent(MenuActivity.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            FirebaseAuth.getInstance().signOut();
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
