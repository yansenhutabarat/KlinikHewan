package com.example.chat.Interface;

import com.example.chat.Model.SlotWaktu;

import java.util.List;

public interface ITimeSlotLoadListener {
    void onTimeSlotLoadSuccess(List<SlotWaktu> slotWaktuList);
    void onTimeSlotLoadFailed(String message);
    void onTimeSlotLoadEmpty();
}
