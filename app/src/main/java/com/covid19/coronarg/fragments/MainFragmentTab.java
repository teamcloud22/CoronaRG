package com.covid19.coronarg.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.covid19.coronarg.R;
import com.covid19.coronarg.adapter.TabAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainFragmentTab extends Fragment {

    private TabAdapter adapter;
    private TabLayout tableLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabs, container, false);

        viewPager = view.findViewById(R.id.request_orders_view_pager);
        tableLayout = view.findViewById(R.id.request_orders_tabs);

        adapter = new TabAdapter(getFragmentManager());
        adapter.addFragment(new CovidKRFCRecycler(), "발생현황");
        adapter.addFragment(new CovidVCRecycler(), "접종현황");
        adapter.addFragment(new CovidHPRecycler(), "위탁의료기관");

        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);

        return view;
    }
}