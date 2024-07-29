package com.example.remind.helpers

import android.util.Log

object PermissionHelper {

    fun handlePermissionResult(isGranted: Boolean) {
        if (isGranted) {
            Log.d("TAG", "Notification permission granted")
        } else {
            Log.d("TAG", "Notification permission denied")
        }
    }
}
