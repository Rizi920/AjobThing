package com.example.jobportal.listeners;

import android.widget.ImageView;

public interface JobsAdapterCallBack {

    void onLoveClickCallback(int position, ImageView love);

    void onJobClickCallback(int position);

}
