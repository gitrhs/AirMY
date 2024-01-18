package com.example.airmy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import Fragments.SevenDaysFragment;
import Fragments.TodayFragment;

public class ViewPageSwitcher extends FragmentStateAdapter {


    public ViewPageSwitcher(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TodayFragment();
            case 1:
                return new SevenDaysFragment();
            default:
                return new TodayFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}