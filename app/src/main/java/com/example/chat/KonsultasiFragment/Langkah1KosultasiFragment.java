package com.example.chat.KonsultasiFragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chat.Adapter.DokterAdapter;
import com.example.chat.Common.Common;
import com.example.chat.Common.SpacesItemDecoration;
import com.example.chat.Model.Dokter;
import com.example.chat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Langkah1KosultasiFragment extends Fragment {

    CollectionReference dokterRef;
    AlertDialog dialog;

    LocalBroadcastManager localBroadcastManager;

    RecyclerView recycler_dokter;

    private BroadcastReceiver dokterDoneReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Dokter> dokterArrayList = intent.getParcelableArrayListExtra(Common.KEY_DOKTER_LOAD_DONE);
            DokterAdapter adapter = new DokterAdapter(getContext(),dokterArrayList);
            recycler_dokter.setAdapter(adapter);

        }
    };

    static Langkah1KosultasiFragment instance;
    public static Langkah1KosultasiFragment getInstance() {
        if (instance == null)
            instance = new Langkah1KosultasiFragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dokterRef = FirebaseFirestore.getInstance().collection("Dokter");
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(dokterDoneReceiver, new IntentFilter(Common.KEY_DOKTER_LOAD_DONE));

    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(dokterDoneReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_konsultasi_langkah1,container,false);
        recycler_dokter = v.findViewById(R.id.recycler_dokter);

        initView();
        loadDokter();

        return v;
    }

    private void initView() {
        recycler_dokter.setHasFixedSize(true);
        recycler_dokter.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recycler_dokter.addItemDecoration(new SpacesItemDecoration(4));
    }

    private void loadDokter() {
        dokterRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Dokter> dokters = new ArrayList<>();
                for (QueryDocumentSnapshot dokterSnapshot:task.getResult())
                {
                    Dokter dokter = dokterSnapshot.toObject(Dokter.class);
                    dokter.setPassword("");
                    dokter.setDokterId(dokterSnapshot.getId());
                    dokters.add(dokter);
                }
                Intent intent = new Intent(Common.KEY_DOKTER_LOAD_DONE);
                intent.putExtra(Common.KEY_DOKTER_LOAD_DONE,dokters);
                localBroadcastManager.sendBroadcast(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();

            }
        });
    }

}
