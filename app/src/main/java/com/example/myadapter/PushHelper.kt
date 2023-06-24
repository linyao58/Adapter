package com.example.myadapter

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.PushAgent
import com.umeng.message.UmengMessageHandler
import com.umeng.message.api.UPushRegisterCallback
import com.umeng.message.entity.UMessage

object PushHelper {

    private val TAG = "PushHelper"

    fun preInit(context: Context){

        UMConfigure.preInit(context, "64971af78e7c224eb40661a8", "地理位置")

    }

    fun init(context: Context){

        UMConfigure.init(context, "64971af78e7c224eb40661a8", "地理位置", UMConfigure.DEVICE_TYPE_PHONE,"b54d22cf834e9df4830aee7dbca4ef26")

//        注册推送
        PushAgent.getInstance(context).register(object : UPushRegisterCallback {
            override fun onSuccess(deviceToken: String?) {
                Log.e(TAG, "注册成功 onSuccess: $deviceToken")
            }

            override fun onFailure(errCode: String?, errDesc: String?) {
                Log.e(TAG, "注册失败 onFailure: $errCode and $errDesc")
            }

        })

    }

}