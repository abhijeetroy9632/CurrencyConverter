package com.paypay.currencyconverter.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paypay.currencyconverter.model.LiveExchangeRatesEntity

@Database(entities = [LiveExchangeRatesEntity::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class Database: RoomDatabase() {
    abstract fun exchangeRateDao(): DatabaseDao
}