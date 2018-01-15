package com.learnit.sakshi.connectinggeeksapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.learnit.sakshi.connectinggeeksapp.event.EventWrapperFragment;
import com.learnit.sakshi.connectinggeeksapp.userevent.UserEventWrapperFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            // user auth state is changed - user is null
            // launch login activity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_events);

        View navigationHeaderView = navigationView.getHeaderView(navigationView.getHeaderCount() -1 );
        TextView userName = (TextView) navigationHeaderView.findViewById(R.id.user_name);
        TextView userEmailId = (TextView) navigationHeaderView.findViewById(R.id.user_email_id);
        ImageView userDp = (ImageView) navigationHeaderView.findViewById(R.id.user_dp_imageView);

        userName.setText(user.getDisplayName());
        userEmailId.setText(user.getEmail());
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentParentViewGroup, new EventWrapperFragment())
                .commit();
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_events) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentParentViewGroup, new EventWrapperFragment())
                    .commit();
        } else if (id == R.id.nav_manage) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentParentViewGroup, new UserEventWrapperFragment())
                    .commit();
        } else if (id == R.id.nav_change_pass) {
//            progressBar.setVisibility(View.VISIBLE);
//            if (user != null && !newPassword.getText().toString().trim().equals("")) {
//                if (newPassword.getText().toString().trim().length() < 6) {
//                    newPassword.setError("Password too short, enter minimum 6 characters");
//                    progressBar.setVisibility(View.GONE);
//                } else {
//                    user.updatePassword(newPassword.getText().toString().trim())
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(MainActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
//                                        signOut();
//                                        progressBar.setVisibility(View.GONE);
//                                    } else {
//                                        Toast.makeText(MainActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
//                }
//            } else if (newPassword.getText().toString().trim().equals("")) {
//                newPassword.setError("Enter password");
//                progressBar.setVisibility(View.GONE);
//            }
//        }

        } else if (id == R.id.nav_account_logout) {
            auth.signOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

}
