package com.rohan.passwordgenerator;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.nav);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.main);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Password Generator");
        toolbar.setTitleTextColor(Color.WHITE);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id =item.getItemId();
                if (id==R.id.myPass){
                    load(new Second(),false);
                }
                if (id==R.id.home){
                    load(new Home(),true);

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            }
        });
        if (savedInstanceState == null) {
            load(new Home(), true); // Load Home as the default fragment
            navigationView.setCheckedItem(R.id.home); // Highlight the Home item in the Navigation Drawer
        }







}

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount()>1) {
                getSupportFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }
    }

    public void load(Fragment fragment, boolean flag){
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    if (flag){
        fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.add(R.id.container,fragment);
    }else {
        ft.replace(R.id.container,fragment);
        ft.addToBackStack(null);
    }
ft.commit();
}

}