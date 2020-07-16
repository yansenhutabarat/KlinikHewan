package com.example.chat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.chat.Common.Common;
import com.example.chat.Interface.RecyclerItemSelectedListener;
import com.example.chat.Model.Dokter;
import com.example.chat.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

public class DokterAdapter extends RecyclerView.Adapter<DokterAdapter.MyViewHolder> {


    Context context;
    List<Dokter> dokterList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public DokterAdapter(Context context, List<Dokter> dokters) {
        this.context = context;
        this.dokterList = dokters;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_dokter, parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
            holder.txt_dokter.setText(dokterList.get(position).getNama());
            holder.ratingBar.setRating((float)dokterList.get(position).getRating());
            if(!cardViewList.contains(holder.ly_dokter))
                cardViewList.add(holder.ly_dokter);

            holder.setRecyclerItemSelectedListener(new RecyclerItemSelectedListener() {
                @Override
                public void onItemSelectedListener(View view, int pos) {
                    for (CardView cardView:cardViewList)
                        //set background sebelum dipiih
                        cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

                    //set background ketika dipilih
                    holder.ly_dokter.setCardBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_dark));

                    //Kirim Broadcast untuk memberitahu KonsultasiActivity
                    Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                    intent.putExtra(Common.KEY_DOKTER_LOAD_DONE, (Parcelable) dokterList.get(pos));
                    intent.putExtra(Common.KEY_LANGKAH,1);
                    localBroadcastManager.sendBroadcast(intent);

                }
            });
    }

    @Override
    public int getItemCount() {
        return dokterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_dokter;
        RatingBar ratingBar;
        CardView ly_dokter;

        RecyclerItemSelectedListener recyclerItemSelectedListener;

        public void setRecyclerItemSelectedListener(RecyclerItemSelectedListener recyclerItemSelectedListener) {
            this.recyclerItemSelectedListener = recyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ly_dokter = (CardView)itemView.findViewById(R.id.ly_dokter);
            txt_dokter = (TextView)itemView.findViewById(R.id.txt_dokter);
            ratingBar = (RatingBar)itemView.findViewById(R.id.rtb_dokter);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerItemSelectedListener.onItemSelectedListener(v,getAdapterPosition());
        }
    }
}
