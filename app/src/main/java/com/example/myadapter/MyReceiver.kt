package com.example.myadapter

import android.content.Context
import cn.jpush.android.api.JPushMessage
import cn.jpush.android.service.JPushMessageReceiver

open class MyReceiver: JPushMessageReceiver() {

    override fun onTagOperatorResult(p0: Context?, p1: JPushMessage?) {
        super.onTagOperatorResult(p0, p1)
    }

    override fun onCheckTagOperatorResult(p0: Context?, p1: JPushMessage?) {
        super.onCheckTagOperatorResult(p0, p1)
    }

    override fun onAliasOperatorResult(p0: Context?, p1: JPushMessage?) {
        super.onAliasOperatorResult(p0, p1)
    }

    override fun onMobileNumberOperatorResult(p0: Context?, p1: JPushMessage?) {
        super.onMobileNumberOperatorResult(p0, p1)
    }

}