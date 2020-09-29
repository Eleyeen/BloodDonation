package com.example.blooddonation.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.blooddonation.R;
import com.example.blooddonation.activities.HomeActivity;
import com.example.blooddonation.adapter.MainTabScreenViewPagerAdapter;
import com.example.blooddonation.fragments.AllDonorFragment;
import com.example.blooddonation.fragments.BloodGroupFragment;
import com.example.blooddonation.fragments.NearByFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    public static SearchView filterSearchView;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, root);
        initListener();
        filterSearchView = root.findViewById(R.id.searchView);



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

                    filterSearchView.setVisibility(View.GONE);

                }
                if (i == 1) {
                    filterSearchView.setVisibility(View.VISIBLE);

                }else {
                    filterSearchView.setVisibility(View.GONE);

                }


                viewPager.setCurrentItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
}