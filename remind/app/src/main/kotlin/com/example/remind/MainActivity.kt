// File: MainActivity.kt
package com.example.remind

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.remind.databinding.ActivityMainBinding
import com.example.remind.helpers.FirebaseHelper
import com.example.remind.helpers.NotificationHelper
import com.example.remind.helpers.PermissionHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        PermissionHelper.handlePermissionResult(isGranted)
    }

    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val actionBar = supportActionBar
        actionBar?.hide()


        NotificationHelper.createNotificationChannel(this)
        askNotificationPermission()
        FirebaseHelper.fetchFCMToken(this)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)

        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home,
            R.id.navigation_todo,
            R.id.navigation_journal,
        ).build()

        val navController = findNavController(this, R.id.nav_host_fragment_activity_main)
//        setupActionBarWithNavController(this, navController, appBarConfiguration)
        setupWithNavController(navView, navController)
        navView.itemIconTintList = null;
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    Log.d("TAG", "Notification permission already granted")
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    Log.d("TAG", "Show educational UI for notification permission")
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }



}
