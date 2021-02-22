package com.paypay.currencyconverter.repository

import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import com.paypay.currencyconverter.ApplicationContextUtil
import com.paypay.currencyconverter.local.preferences.AppSharedPreferences
import com.paypay.currencyconverter.model.LiveExchangeRatesEntity
import com.paypay.currencyconverter.network.RetrofitApiClient
import com.paypay.currencyconverter.local.room.DatabaseClient
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * This is a repository class which decides and fetches data from appropriate data source
 */
class CurrencyConverterRepository {

    companion object{
        private const val TIME_LIMIT = 30L
        private const val LOG_TAG = "CurrencyConverterRepository"

        // Need this application context to initialize Room DB and Shared Preferences
        private val appContext: Context by lazy {
            ApplicationContextUtil.getAppContext()
        }
    }

    /**
     * Decides the data source(remote or local) from which the data to be fetched based on time limit
     */
    fun fetchLiveExchangeRates(): Single<LiveExchangeRatesEntity> {
        return if (hasExceededTimeLimit(TIME_LIMIT)) {
            Log.d(LOG_TAG, "Reading from Remote")
            fetchFromRemote()
        } else {
            Log.d(LOG_TAG, "Reading from DB")
            fetchFromDb()
        }
    }

    /**
     * Makes network call to fetch the data and after that store the same in local DB
     */
    private fun fetchFromRemote(): Single<LiveExchangeRatesEntity> {
        val appContext = ApplicationContextUtil.getAppContext()
        val retrofitApiClient =  RetrofitApiClient()
        return retrofitApiClient.providesRetrofitApiService()
            .fetchLiveExchangeRates(retrofitApiClient.getApiKey())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnSuccess {
                // On successful network call, storing the data in DB
                DatabaseClient
                    .getDatabaseInstance(appContext)
                    .exchangeRateDao()
                    .insertQuotes(it)
                // Storing the timestamp in shared preference
                AppSharedPreferences.storeTimeStamp(appContext, Calendar.getInstance().timeInMillis)
            }
    }

    /**
     * Reads and returns data from DB
     */
    private fun fetchFromDb(): Single<LiveExchangeRatesEntity> {
        return DatabaseClient
            .getDatabaseInstance(appContext)
            .exchangeRateDao()
            .getQuotes()
            .doOnError{
                fetchFromRemote()
            }
    }

    /**
     * Checks it the time limit to read from DB is exceeded
     */
    private fun hasExceededTimeLimit(timeLimit: Long): Boolean {
        val timestamp = AppSharedPreferences.getTimeStamp(appContext)
        var diffInMinutes = 0L
        if(timestamp != -1L) {
            val diff = Calendar.getInstance().timeInMillis - timestamp
            diffInMinutes = (diff / 1000) / 60
        }
        return (timestamp == -1L || diffInMinutes >= timeLimit)
    }
}