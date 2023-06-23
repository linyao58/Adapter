package com.example.myadapter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import cn.jpush.android.api.JPushInterface

open class MyPushReceiver: BroadcastReceiver() {

    private val TAG = "pushReceiver"

    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.extras

        when(intent?.action){
            JPushInterface.EXTRA_REGISTRATION_ID ->{
                val regId = bundle?.getString(JPushInterface.EXTRA_REGISTRATION_ID)
                Log.e(TAG, "onReceive: $regId")
            }
            JPushInterface.ACTION_MESSAGE_RECEIVED -> {
                val msg = bundle?.getString(JPushInterface.EXTRA_MESSAGE)
                Log.e(TAG, "onReceive: $msg" )
                if (bundle != null && msg != null) {
                    processCustomMessage(context, msg)
                }
            }
            JPushInterface.ACTION_NOTIFICATION_RECEIVED -> {
                Log.e(TAG, "onReceive: 用户收到通知")
                val notificationId = bundle?.getInt(JPushInterface.EXTRA_NOTIFICATION_ID)
                Log.e(TAG, "onReceive: $notificationId")
            }
            JPushInterface.ACTION_NOTIFICATION_OPENED -> {
                val intent = Intent(context, MainActivity2::class.java)
                context?.startActivity(intent)
            }
            else ->{
                Log.e(TAG, "onReceive: Error")
            }
        }
    }

    private fun processCustomMessage(context: Context?, message: String){

        val mNotificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val mChannel = NotificationChannel("chat", "chat message", NotificationManager.IMPORTANCE_DEFAULT)
            mNotificationManager.createNotificationChannel(mChannel)

        }

        val intent = Intent(context, MainActivity2::class.java)
        val contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val mBuilder = NotificationCompat.Builder(context, "chat")
            .setSmallIcon(R.drawable.r_c)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.r_c))
            .setContentTitle("今天学习")
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(contentIntent)
            .build()

        mNotificationManager.notify(1, mBuilder)

    }

}