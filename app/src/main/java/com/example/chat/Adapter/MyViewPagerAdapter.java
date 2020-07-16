package com.example.chat.Adapter;

import com.example.chat.KonsultasiFragment.Langkah1KosultasiFragment;
import com.example.chat.KonsultasiFragment.Langkah2KonsultasiFragment;
import com.example.chat.KonsultasiFragment.Langkah3KonsultasiFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    public MyViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return Langkah1KosultasiFragment.getInstance();
            case 1:
                return Langkah2KonsultasiFragment.getInstance();
            case 2:
                return Langkah3KonsultasiFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
