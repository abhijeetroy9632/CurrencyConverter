package com.paypay.currencyconverter.local.preferences

import android.content.Context

object AppSharedPreferences {
    private const val APP_PREFERENCE_KEY = "com.paypay.currencyconverter.PREFERENCE"
    fun getTimeStamp(context: Context):  Long {
        val pref = context.getSharedPreferences(APP_PREFERENCE_KEY, Context.MODE_PRIVATE)
        return pref.getLong("timestamp", -1L)
    }

    fun storeTimeStamp(context: Context, timestamp: Long) {
        val pref = context.getSharedPreferences(APP_PREFERENCE_KEY, Context.MODE_PRIVATE)
        pref.edit().putLong("timestamp", timestamp).apply()
    }
}