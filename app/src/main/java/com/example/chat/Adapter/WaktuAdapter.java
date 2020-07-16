package com.example.chat.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chat.Common.Common;
import com.example.chat.Model.SlotWaktu;
import com.example.chat.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class WaktuAdapter extends RecyclerView.Adapter<WaktuAdapter.MyViewHolder> {

    Context context;
    List<SlotWaktu> slotWaktuList;

    public WaktuAdapter(Context context) {
        this.context = context;
        this.slotWaktuList = new ArrayList<>();
    }

    public WaktuAdapter(Context context, List<SlotWaktu> slotWaktuList) {
        this.context = context;
        this.slotWaktuList = slotWaktuList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_waktu, parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_waktu.setText(new StringBuilder(Common.convertTimeSlotToString(i)).toString());
        if (slotWaktuList.size() == 0) //jika semua waktu tersedia, maka tampilkan list
        {
            myViewHolder.card_waktu.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
            myViewHolder.txt_deskripsi_waktu.setText("Available");
            myViewHolder.txt_deskripsi_waktu.setTextColor(context.getResources().getColor(android.R.color.black));
            myViewHolder.txt_waktu.setTextColor(context.getResources().getColor(android.R.color.black));

        }
        else //jika waktu penuh (telah dipesan)
        {
            for (SlotWaktu slotWaktu:slotWaktuList)
            {
                //atur warna jika telah dipesan
                int slot = Integer.parseInt(String.valueOf(slotWaktu.getSlot()));
                if (slot == i){
                    myViewHolder.card_waktu.setCardBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
                    myViewHolder.txt_deskripsi_waktu.setText("Penuh");
                    myViewHolder.txt_deskripsi_waktu.setTextColor(context.getResources().getColor(android.R.color.white));
                    myViewHolder.txt_waktu.setTextColor(context.getResources().getColor(android.R.color.white));
                }

            }

        }
    }

    @Override
    public int getItemCount() {
        return Common.WAKTU_SLOT_TOTAL;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_waktu,txt_deskripsi_waktu;
        CardView card_waktu;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_waktu = itemView.findViewById(R.id.card_waktu);
            txt_waktu = itemView.findViewById(R.id.txt_waktu);
            txt_deskripsi_waktu = itemView.findViewById(R.id.txt_deskripsi_waktu);
        }
    }
}
