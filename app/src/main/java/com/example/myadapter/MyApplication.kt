package com.example.myadapter

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import cn.jpush.android.api.JPushInterface
import com.pgyersdk.crash.PgyCrashManager
import com.tencent.bugly.Bugly
import com.tencent.bugly.crashreport.CrashReport
import com.umeng.commonsdk.UMConfigure
import kotlin.concurrent.thread

open class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

//        CrashReport.initCrashReport(applicationContext)

        Bugly.init(applicationContext, "798f55ed9e", false)

        PgyCrashManager.register(applicationContext)

        JPushInterface.setDebugMode(true)
        JPushInterface.init(applicationContext)

        initUmengSDK()

    }

    private fun initUmengSDK(){
//      友盟日志开关
        UMConfigure.setLogEnabled(true)
//      预初始化，不采集信息
        PushHelper.preInit(this)

//        判断是否同意隐私政策
        val agreed = true
        if (!agreed){
            return
        }

        thread {
            PushHelper.init(applicationContext)
        }

    }

}