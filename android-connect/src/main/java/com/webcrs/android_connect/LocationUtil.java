package com.webcrs.android_connect;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.security.Permission;

public class LocationUtil {
    private static LocationUtil locationUtil = null;
    Context context;

    public LocationUtil(Context context) {
        this.context = context;
    }

    /* private static LocationUtil getInstance() {
        if (locationUtil == null)
            locationUtil = new LocationUtil();

        return locationUtil;
    }*/

    public boolean isLocationActivated() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission_group.LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Location permission not granted.", Toast.LENGTH_SHORT).show();
            return false;
        }
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            if (lm != null) {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            }
        } catch (Exception ex) {
            Log.d("GPS Provider", ex.getLocalizedMessage());
        }

        try {
            if (lm != null) {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            }
        } catch (Exception ex) {
            Log.d("Network Provider", ex.getLocalizedMessage());
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(context)
                    .setMessage(R.string.gps_network_not_enabled)
                    .setPositiveButton(R.string.open_location_settings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton(R.string.Cancel, null)
                    .show();
        }

        return gps_enabled;
    }

}
