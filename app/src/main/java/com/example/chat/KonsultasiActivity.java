package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import dmax.dialog.SpotsDialog;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chat.Adapter.MyViewPagerAdapter;
import com.example.chat.Common.Common;
import com.example.chat.Common.NonSwipeViewPager;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class KonsultasiActivity extends AppCompatActivity {

    AlertDialog dialog;
    CollectionReference dokterRef;

    LocalBroadcastManager localBroadcastManager;

    StepView stepView;
    NonSwipeViewPager viewPager;
    Button btn_sebelumnya;
    Button btn_selanjutnya;

    //Broadcast Receiver
    private BroadcastReceiver buttonNextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int langkah = intent.getIntExtra(Common.KEY_LANGKAH,0);
            if (langkah == 0)
                Common.currentDokter = intent.getParcelableExtra(Common.KEY_DOKTER_LOAD_DONE);
            else if (langkah == 1)
                Common.currentdokter = intent.getParcelableExtra(Common.KEY_DOKTER_SELECTED);
            btn_selanjutnya.setEnabled(true);
            setColorButton();
        }
    };

    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(buttonNextReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsultasi);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(buttonNextReceiver, new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));

        dialog = new SpotsDialog.Builder().setContext(this).build();

        stepView = findViewById(R.id.step_view);
        viewPager = findViewById(R.id.view_pager);
        btn_sebelumnya = findViewById(R.id.btn_sebelumnya);
        btn_selanjutnya = findViewById(R.id.btn_selanjutnya);

        btn_sebelumnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.step == 2 || Common.step > 0){
                    Common.step--;
                    viewPager.setCurrentItem(Common.step);
                }
            }
        });
        btn_selanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.step < 2 || Common.step == 0){
                    Common.step++;
                    if (Common.step == 1){
                        if (Common.currentdokter != null)
                            loadTimeSlotOfDokter(Common.currentdokter.getDokterId());
                    }
                    //2

                    viewPager.setCurrentItem(Common.step);
                }
            }

            //2
            private void loadTimeSlotOfDokter(String dokterId) {
                //kirim Local Broadcast ke langkah 2 fragment
                dialog.show();
                Intent intent = new Intent(Common.KEY_DISPLAY_TIME_SLOT);
                localBroadcastManager.sendBroadcast(intent);
            }

//            private void loadDokterFirst(String dokterId) {
//                dialog.show();
//                dokterRef = FirebaseFirestore.getInstance().collection("Dokter");
//            }
        });

        setupStepView();
        setColorButton();

        //view
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                //Tampilkan Step
                stepView.go(i,true);
                if (i == 0)
                    btn_sebelumnya.setEnabled(false);
                else
                    btn_sebelumnya.setEnabled(true);

                setColorButton();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setColorButton() {
        if (btn_selanjutnya.isEnabled())
        {
            btn_selanjutnya.setBackgroundResource(R.color.colorButton);
        }
        else
        {
            btn_selanjutnya.setBackgroundResource(R.color.darker_grey);
        }
        if (btn_sebelumnya.isEnabled())
        {
            btn_sebelumnya.setBackgroundResource(R.color.colorButton);
        }
        else
        {
            btn_sebelumnya.setBackgroundResource(R.color.darker_grey);
        }
    }

    private void setupStepView() {
        List<String> stepList = new ArrayList<>();
        stepList.add("Dokter");
        stepList.add("Waktu");
        stepList.add("Konfirmasi");
        stepView.setSteps(stepList);
    }

}
