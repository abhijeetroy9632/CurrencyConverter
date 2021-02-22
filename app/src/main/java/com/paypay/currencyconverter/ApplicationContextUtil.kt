package com.paypay.currencyconverter

import android.content.Context

object ApplicationContextUtil {
    private lateinit var appContext: Context

    fun initAppContext(context: Context) {
        appContext = context
    }

    fun getAppContext(): Context {
        return appContext
    }
}