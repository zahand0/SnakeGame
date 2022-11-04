package com.example.snakegame

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavHostController
import com.example.snakegame.navigation.SetupNavGraph
import com.example.snakegame.presentation.theme.SnakeGameTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.onesignal.OneSignal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    companion object {
        private const val TAG = "MainActivity"
    }

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app: ApplicationInfo = this.packageManager
            .getApplicationInfo(this.packageName, PackageManager.GET_META_DATA)
        val bundle = app.metaData
        val onesignalAppID = bundle.getString("ONESIGNAL_APP_ID")
        firebaseAnalytics = Firebase.analytics
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            // Log and toast
            val msg = "msg token $token"
            Log.d(TAG, msg)
        })
        onesignalAppID?.let {
            // Logging set to help debug issues, remove before releasing your app.
            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

            // OneSignal Initialization
            OneSignal.initWithContext(this)
            OneSignal.setAppId(onesignalAppID)
        }
        setContent {
            SnakeGameTheme {
                navController = rememberAnimatedNavController()
                Surface(
                    color = MaterialTheme.colors.surface
                ) {
                    SetupNavGraph(
                        navController = navController,
                        firebaseAnalytics = firebaseAnalytics
                    )
                }
            }
        }
    }
}
