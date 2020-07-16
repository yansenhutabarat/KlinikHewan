package com.example.chat.KonsultasiFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chat.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Langkah3KonsultasiFragment extends Fragment {

    static Langkah3KonsultasiFragment instace;

    public static Langkah3KonsultasiFragment getInstance() {
        if (instace == null)
            instace = new Langkah3KonsultasiFragment();
        return instace;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_konsultasi_langkah3,container,false);
    }
}
