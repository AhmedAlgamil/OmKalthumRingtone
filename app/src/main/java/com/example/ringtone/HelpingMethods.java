package com.example.ringtone;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

public class HelpingMethods {

    public static void onPermission(Activity activity) {
        String[] perms = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.CHANGE_CONFIGURATION,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_SETTINGS};

        ActivityCompat.requestPermissions(activity,
                perms,
                100);
    }

    public static void makePermission(Activity activity) {

    }

}
