package com.example.remind.helpers

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.example.remind.R

object FirebaseHelper {

    fun fetchFCMToken(context: Context) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result
            TokenManager.saveToken(context, token)
            val msg = context.getString(R.string.msg_token_fmt, token)
            Log.d("TAG", msg)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        })
    }



    fun removeToken(context: Context) {

        FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Deleting FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            Log.d("TAG", "FCM registration token deleted")
            Toast.makeText(context, "FCM registration token deleted", Toast.LENGTH_SHORT).show()
        }
    }
}
