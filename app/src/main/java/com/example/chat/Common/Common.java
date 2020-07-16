package com.example.chat.Common;

import com.example.chat.Model.Dokter;

public class Common {
    public static final String KEY_DOKTER_LOAD_DONE = "DOKTER_LOAD_DONE";
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_DISPLAY_TIME_SLOT = "DISPLAY_TIME_SLOT";
    public static final String KEY_LANGKAH = "LANGKAH";
    public static final String KEY_DOKTER_SELECTED = "DOKTER_SELECTED";
    public static final int WAKTU_SLOT_TOTAL = 16;
    public static Dokter currentDokter;
    public static int step = 0;
    public static Dokter currentdokter;

    public static String convertTimeSlotToString(int slot) {
        switch (slot)
        {
            case 0:
                return "09:00-09:30";
            case 1:
                return "09:30-10.00";
            case 2:
                return "10:00-10:30";
            case 3:
                return "10:30-11.00";
            case 4:
                return "11:00-11:30";
            case 5:
                return "11:30-12.00";
            case 6:
                return "12:00-12:30";
            case 7:
                return "12:30-13.00";
            case 8:
                return "13:00-13:30";
            case 9:
                return "13:30-14.00";
            case 10:
                return "14:00-14:30";
            case 11:
                return "14:30-15.00";
            case 12:
                return "15:00-15:30";
            case 13:
                return "15:30-16.00";
            case 14:
                return "16:00-16:30";
            case 15:
                return "16:30-17.00";
            default:
                return "Closed";
        }
    }
}
