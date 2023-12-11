package com.example.airmy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import Fragments.NewsPage1Fragment;
import Fragments.NewsPage2Fragment;

public class ViewPageSwitcher2 extends FragmentStateAdapter {


    public ViewPageSwitcher2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new NewsPage1Fragment();
            case 1:
                return new NewsPage2Fragment();
            default:
                return new NewsPage1Fragment();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}