package com.drpro.laundryin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.drpro.laundryin.Common.Common;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTxtFullname;
    private static long back_pressed;
    private int mTabPosition = 0;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   // mTextMessage.setText("Homeeeeeeeeeeeeeee");
                    mTabPosition = 0;
                    HomeFragment homeFrag = new HomeFragment();
                   FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                   ft.replace(R.id.content, homeFrag, "FragmentName");
                   ft.commit();
                return true;
                case R.id.navigation_dashboard:
                  //  mTextMessage.setText("Historyyyyyyyyyyyyyy");
                    if(mTabPosition ==1)
                        return false;
                    else{
                    mTabPosition = 1;
                    BlankFragment bleng = new BlankFragment();
                    FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                   ft2.replace(R.id.content, bleng, "FragmentName");
                   ft2.commit();}
                    return true;
                case R.id.navigation_notifications:
                  //  mTextMessage.setText("Profileeeeeeeeeeeeeeeeeeeeeee");
                    if(mTabPosition ==2)
                        return false;
                    else{
                    mTabPosition = 2;
                    ProfileFragment profile = new ProfileFragment();
                    FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                    ft3.replace(R.id.content, profile, "FragmentName");
                    ft3.commit();}
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        mTxtFullname = headerView.findViewById(R.id.txtFullName);
        mTxtFullname.setText(Common.currentUser.getName().toString());


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (back_pressed + 2000 > System.currentTimeMillis()) {
            this.moveTaskToBack(true);
        }
        else
            Toast.makeText(this, "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

        if (id == R.id.Profile) {
            ProfileFragment profile = new ProfileFragment();
            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
            ft3.replace(R.id.content, profile, "FragmentName");
            ft3.commit();
        } else if (id == R.id.Payment) {

        } else if (id == R.id.Notification) {

        } else if (id == R.id.Telephone) {

            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=6285691220706&text=Hallo%20LaundryIN%20");
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(sendIntent);

        } else if (id == R.id.Email) {

        } else if (id == R.id.Chat) {

        } else if (id == R.id.Help) {

        } else if (id == R.id.Promotion) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
