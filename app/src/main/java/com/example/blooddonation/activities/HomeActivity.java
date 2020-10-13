package com.example.blooddonation.activities;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.blooddonation.BuildConfig;
import com.example.blooddonation.R;
import com.example.blooddonation.utils.GeneralUtills;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;

    @BindView(R.id.navName)
    TextView tvNavName;
    @BindView(R.id.tvNavEmail)
    TextView tvNavEmail;
    @BindView(R.id.civProfileHeader)
    CircleImageView civProfileHeader;
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

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_logout, R.id.navTeramAndCondition)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_logout) {
                    Logout();
                } else if (destination.getId() == R.id.navTeramAndCondition) {
                    startActivity(new Intent(HomeActivity.this, TermAndConditionActivity.class));
                    Toast.makeText(HomeActivity.this, "termAndCondition", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setNavText() {
        String name = GeneralUtills.getUserName(this);
        tvNavName.setText(name);
        tvNavEmail.setText(GeneralUtills.getUserEmail(this));
        Glide.with(this).load(GeneralUtills.getSharedPreferences(this).getString("user_profile_image","")).into(civProfileHeader);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_RateUs:
                rateNowLaunchMarket();
                break;

            case R.id.action_share:
                shareApp();
                break;

            case R.id.action_aboutUs:

                startActivity(new Intent(HomeActivity.this, AboutActivity.class));

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu_search) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu_search);

        return super.onCreateOptionsMenu(menu_search);
    }

    private void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String shareMessage = "\nBlood Donation App\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void Logout() {
        GeneralUtills.putBooleanValueInEditor(this, "isLogin", false).commit();

        finishAffinity();

        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void rateNowLaunchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find app in play store", Toast.LENGTH_LONG).show();
        }
    }


}