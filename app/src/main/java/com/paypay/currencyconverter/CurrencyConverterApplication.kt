package com.paypay.currencyconverter

import android.app.Application

class CurrencyConverterApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }
    private fun init() {
        ApplicationContextUtil.initAppContext(this)
    }
}