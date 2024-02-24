package khatoco.tvc.com.khatoco.ui.activity;

import android.app.Application;

import java.net.CookiePolicy;

import khatoco.tvc.com.khatoco.utils.ConnectivityReceiver;

/**
 * Created by prosoft on 11/11/16.
 */

public class KhatocoApplication  extends Application {

    private static KhatocoApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static java.net.CookieManager msCookieManager = new java.net.CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER);

    public static synchronized KhatocoApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}

