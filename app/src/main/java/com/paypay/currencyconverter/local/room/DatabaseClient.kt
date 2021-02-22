package com.paypay.currencyconverter.local.room

import android.content.Context
import androidx.room.Room

object DatabaseClient {

    private const val EXCHANGE_RATE_DATABASE = "ExchangeRateDatabase"
    fun getDatabaseInstance(context: Context): Database {
        return  Room.databaseBuilder(
            context,
            Database::class.java, EXCHANGE_RATE_DATABASE
        ).build()
    }
}