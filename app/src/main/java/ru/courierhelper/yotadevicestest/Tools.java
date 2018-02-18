package ru.courierhelper.yotadevicestest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Ivan on 15.02.2018.
 */

public class Tools {

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static void showInternetRequiredSnackbar(View view){
        Snackbar.make(view,
                view.getContext().getString(R.string.no_internet_snackbar),
                Snackbar.LENGTH_SHORT)
                .show();
    }

    public static void showErrorSnackbar(View view){
        Snackbar.make(view,
                view.getContext().getResources().getString(R.string.error),
                Snackbar.LENGTH_SHORT)
                .show();
    }

}
