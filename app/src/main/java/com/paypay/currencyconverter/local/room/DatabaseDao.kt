package com.paypay.currencyconverter.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paypay.currencyconverter.model.LiveExchangeRatesEntity
import io.reactivex.Single

@Dao
interface DatabaseDao {
    @Insert (
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insertQuotes(quotes: LiveExchangeRatesEntity)

    @Query("SELECT * FROM LiveExchangeRatesTable")
    fun getQuotes(): Single<LiveExchangeRatesEntity>
}