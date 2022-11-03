package com.example.snakegame

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class SnakeGameApp: Application() {
    companion object {
        private const val TAG = "SnakeGameApp"
    }

    override fun onCreate() {
        super.onCreate()
        val app: ApplicationInfo = this.packageManager
            .getApplicationInfo(this.packageName, PackageManager.GET_META_DATA)
        val bundle = app.metaData
        val devKey = bundle.getString("AF_DEV_KEY")
        devKey?.let {
            AppsFlyerLib.getInstance().init(devKey, null, this)
            AppsFlyerLib.getInstance().setDebugLog(true)
            AppsFlyerLib.getInstance().start(this, devKey, object : AppsFlyerRequestListener {
            override fun onSuccess() {
                Log.d(TAG, "Launch sent successfully")
            }

            override fun onError(errorCode: Int, errorDesc: String) {
                Log.d(TAG, "Launch failed to be sent:\n" +
                        "Error code: " + errorCode + "\n"
                        + "Error description: " + errorDesc)
            }
        })
        }
    }
}