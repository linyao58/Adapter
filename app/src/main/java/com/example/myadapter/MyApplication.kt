package com.example.myadapter

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.tencent.bugly.Bugly
import com.tencent.bugly.crashreport.CrashReport

open class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

//        CrashReport.initCrashReport(applicationContext)

        Bugly.init(applicationContext, "798f55ed9e", false)

    }

}