package com.example.blooddonation.Fragments.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.blooddonation.Adapter.MainTabScreenViewPagerAdapter;
import com.example.blooddonation.Fragments.AllDonorFragment;
import com.example.blooddonation.Fragments.BloodGroupFragment;
import com.example.blooddonation.Fragments.NearByFragment;
import com.example.blooddonation.R;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment  implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, root);
        initListener();

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);



        return root;
    }

    private void initListener() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
        }
    }

    private void setupViewPager(final ViewPager viewPager) {

//        SharedPreferencesUtils.putStringValueInEditor(MainTabActivity.this, "image_sorting", "views");
//        fullViewWallpaperDataModels = ViewsFragment.viewWallpaperDataModels;

        MainTabScreenViewPagerAdapter adapter = new MainTabScreenViewPagerAdapter(getFragmentManager());

        adapter.addFragment(new BloodGroupFragment(), "Blood Group");
        adapter.addFragment(new AllDonorFragment(), "All Donor");
        adapter.addFragment(new NearByFragment(), "Near By");

        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                if (i == 0) {
//                    SharedPreferencesUtils.putStringValueInEditor(MainTabActivity.this, "image_sorting", "views");
//                    fullViewWallpaperDataModels = ViewsFragment.viewWallpaperDataModels;

                }
                if (i == 1) {
//                    SharedPreferencesUtils.putStringValueInEditor(MainTabActivity.this, "image_sorting", "random");
//                    fullViewWallpaperDataModels = RandomFragment.topListWallpaperDataModels;

                }


                viewPager.setCurrentItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
}