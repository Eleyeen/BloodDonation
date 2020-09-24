package com.example.blooddonation.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonation.R;
import com.example.blooddonation.Utails.GeneralUtills;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;

    @BindView(R.id.navName)
    TextView tvNavName;
    @BindView(R.id.tvNavEmail)
    TextView tvNavEmail;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        View headerView = navigationView.getHeaderView(0);
        ButterKnife.bind(this, headerView);
        setNavText();

        String strUserId = GeneralUtills.getSharedPreferences(HomePageActivity.this).getString("user_id", "");
        Toast.makeText(HomePageActivity.this, strUserId, Toast.LENGTH_SHORT).show();

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_BloodGroupp, R.id.nav_allDonorSideBar, R.id.nav_LogOutSideBar, R.id.nav_NearByy, R.id.nav_teamSideBar)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void setNavText() {
        String name = GeneralUtills.getUserName(this);

        tvNavName.setText(name);
        tvNavEmail.setText(GeneralUtills.getUserEmail(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(GeneralUtills.isLogin(this)){
            getMenuInflater().inflate(R.menu.menu_login, menu);

        }else {
            getMenuInflater().inflate(R.menu.menu, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                LOGOUT();
                break;

            case R.id.action_Login:
                startActivity(new Intent(HomePageActivity.this,LoginActivity.class));
                break;

            case R.id.action_SignUp:
                startActivity(new Intent(HomePageActivity.this,SignUpActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void LOGOUT() {
        GeneralUtills.putBooleanValueInEditor(this,"isLogin", false).commit();

        finishAffinity();

        Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
        startActivity(intent);

    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}