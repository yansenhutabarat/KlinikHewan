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
import android.widget.Toast;

import com.example.chat.Adapter.WaktuAdapter;
import com.example.chat.Common.Common;
import com.example.chat.Common.SpacesItemDecoration;
import com.example.chat.Interface.ITimeSlotLoadListener;
import com.example.chat.Model.SlotWaktu;
import com.example.chat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import dmax.dialog.SpotsDialog;

public class Langkah2KonsultasiFragment extends Fragment implements ITimeSlotLoadListener {

    //Variable
    DocumentReference dokterRef;
    ITimeSlotLoadListener iTimeSlotLoadListener;
    AlertDialog dialog;
    LocalBroadcastManager localBroadcastManager;
    Calendar pilih_tanggal;
    RecyclerView recycler_waktu;
    HorizontalCalendarView calendarView;
    SimpleDateFormat simpleDateFormat;

    BroadcastReceiver displayTimeSlot = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DATE,0);//menambahkan tanggal sekarang
            loadAvailableTimeSlotOfDokter(Common.currentdokter.getDokterId(),simpleDateFormat.format(date.getTime()));
        }
    };

    private void loadAvailableTimeSlotOfDokter(String dokterId, final String waktu) {
        dialog.show();
        //  /Dokter/BABsnGs573Wbzrcur0DN
        dokterRef = FirebaseFirestore.getInstance().collection("Dokter").document(Common.currentdokter.getDokterId());

        //get informasi dari dokter
        dokterRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) //jika dokter tersedia
                    {
                        //get informasi konsultasi
                        //jika tidak ditambahkan, maka return empty
                        CollectionReference date = FirebaseFirestore.getInstance()
                                .collection("Dokter").document(Common.currentdokter.getDokterId()).collection(waktu);

                        date.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    QuerySnapshot querySnapshot = task.getResult();
                                    if (querySnapshot.isEmpty()) //jika tidak ada proses komsultasi
                                        iTimeSlotLoadListener.onTimeSlotLoadEmpty();
                                    else
                                    {
                                        //jika ada proses konsultasi
                                        List<SlotWaktu> slotWaktu = new ArrayList<>();
                                        for (QueryDocumentSnapshot document:task.getResult())slotWaktu.add(document.toObject(SlotWaktu.class));
                                        iTimeSlotLoadListener.onTimeSlotLoadSuccess(slotWaktu);
                                    }
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                iTimeSlotLoadListener.onTimeSlotLoadFailed(e.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }

    static Langkah2KonsultasiFragment instace;
    public static Langkah2KonsultasiFragment getInstance() {
        if (instace == null)
            instace = new Langkah2KonsultasiFragment();
        return instace;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iTimeSlotLoadListener = this;

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(displayTimeSlot,new IntentFilter(Common.KEY_DISPLAY_TIME_SLOT));

        simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");

        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();

        pilih_tanggal = Calendar.getInstance();
        pilih_tanggal.add(Calendar.DATE,0);//init tanggal sekarang

    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(displayTimeSlot);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_konsultasi_langkah2,container,false);
        recycler_waktu = v.findViewById(R.id.recycler_waktu);
        calendarView = v.findViewById(R.id.kalenderView);


        init(v);
        return v;
    }

    private void init(View v) {
        recycler_waktu.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recycler_waktu.setLayoutManager(gridLayoutManager);
        recycler_waktu.addItemDecoration((new SpacesItemDecoration(8)));
        


        //Kalender
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE,0);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE,2);//sisa waktu 2 hari


        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(v,R.id.kalenderView)
                .range(startDate,endDate)
                .datesNumberOnScreen(1)
                .mode(HorizontalCalendar.Mode.DAYS)
                .defaultSelectedDate(startDate)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                if (pilih_tanggal.getTimeInMillis() != date.getTimeInMillis()){
                    pilih_tanggal = date;
                    loadAvailableTimeSlotOfDokter(Common.currentdokter.getDokterId(),simpleDateFormat.format(date.getTime()));
                }
            }
        });
    }

    @Override
    public void onTimeSlotLoadSuccess(List<SlotWaktu> slotWaktuList) {
        WaktuAdapter adapter = new WaktuAdapter(getContext(),slotWaktuList);
        recycler_waktu.setAdapter(adapter);


        dialog.dismiss();

    }

    @Override
    public void onTimeSlotLoadFailed(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
        dialog.dismiss();

    }

    @Override
    public void onTimeSlotLoadEmpty() {
        WaktuAdapter adapter = new WaktuAdapter(getContext());
        recycler_waktu.setAdapter(adapter);

        dialog.dismiss();

    }
}
