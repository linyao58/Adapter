package com.example.myadapter

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import cn.jpush.android.api.JPushInterface
import com.pgyersdk.crash.PgyCrashManager
import com.tencent.bugly.Bugly
import com.tencent.bugly.crashreport.CrashReport

open class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

//        CrashReport.initCrashReport(applicationContext)

        Bugly.init(applicationContext, "798f55ed9e", false)

        PgyCrashManager.register(applicationContext)

        JPushInterface.setDebugMode(true)
        JPushInterface.init(applicationContext)

    }

}