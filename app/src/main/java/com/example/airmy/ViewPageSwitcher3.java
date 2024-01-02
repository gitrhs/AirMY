package com.example.airmy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import Fragments.NewsPage1Fragment;
import Fragments.NewsPage2Fragment;
import Fragments.UserLikes;
import Fragments.UserPhotos;

public class ViewPageSwitcher3 extends FragmentStateAdapter {


    public ViewPageSwitcher3(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new UserPhotos();
            case 1:
                return new UserLikes();
            default:
                return new UserLikes();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}