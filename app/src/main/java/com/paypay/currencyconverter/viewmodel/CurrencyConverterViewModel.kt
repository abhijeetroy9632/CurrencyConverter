package com.paypay.currencyconverter.viewmodel

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paypay.currencyconverter.model.LiveExchangeRatesEntity
import com.paypay.currencyconverter.repository.CurrencyConverterRepository
import com.paypay.currencyconverter.ui.adapter.itemstate.BaseItemState
import com.paypay.currencyconverter.ui.adapter.itemstate.ConversionListItemState
import com.paypay.currencyconverter.ui.adapter.itemstate.CurrencyConverterViewState
import com.paypay.currencyconverter.ui.adapter.itemstate.SeparatorItemState
import com.paypay.currencyconverter.ui.adapter.viewholder.CurrencyConverterViewType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CurrencyConverterViewModel : BaseViewModel() {

    // Keeping mutable live data as private and made non mutable exposed
    private val _currencyConverterLiveData = MutableLiveData<CurrencyConverterViewState>()
    val currencyConverterLiveData : LiveData<CurrencyConverterViewState>
    get() = _currencyConverterLiveData

    lateinit var currencyConverterRepository: CurrencyConverterRepository

    companion object {
        private const val LOG_TAG = "CurrencyConverterViewModel"
    }

    /**
     * Calls repository to fetch data without knowing the source of the data
     */
    fun fetchLiveExchangeRates() {
        currencyConverterRepository = CurrencyConverterRepository()
        launchJob(
            currencyConverterRepository.fetchLiveExchangeRates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                        liveExchangeRates -> convertToViewState(liveExchangeRates)
                }
                .subscribe({
                        response -> _currencyConverterLiveData.value = response
                    }, {
                        error -> Log.e(LOG_TAG, "Error while fetching the data", error)
                    }
                )
        )
    }


    /**
     * Transforms the LiveExchangeRatesEntity into CurrencyConverterViewState required to inflate the UI
     */
    @VisibleForTesting
    fun convertToViewState(liveExchangeRatesEntity: LiveExchangeRatesEntity): CurrencyConverterViewState {
        val quotesJsonObject = liveExchangeRatesEntity.quotes.asJsonObject
        val availableCurrencies = quotesJsonObject.keySet().toList<String>()
        val exchangeRates = ArrayList<Double>()
        val transformedCurrencies = ArrayList<String>()
        for(currency in availableCurrencies) {
            exchangeRates.add(quotesJsonObject[currency].toString().toDouble())
            transformedCurrencies.add(currency.substring(3))
        }
        val conversionListItemStates = ArrayList<BaseItemState>()
        for(index in transformedCurrencies.indices) {
            conversionListItemStates.add(ConversionListItemState(transformedCurrencies[index], exchangeRates[index]))
            conversionListItemStates.add(SeparatorItemState())
        }
        return CurrencyConverterViewState(transformedCurrencies, conversionListItemStates)
    }

    /**
     * Invoked whenever user changes selection of currency from spinner and updates the live data to update the UI
     */
    @Suppress("UNCHECKED_CAST")
    fun onCurrencyChanged(selectedCurrency: String, amountText: String) {
        if (amountText != ".") {
            val conversionListItemStateList =
                _currencyConverterLiveData.value?.conversionListItemStateList?.filter {
                    it.viewType == CurrencyConverterViewType.EXCHANGE_RATE
                } as List<ConversionListItemState>

            val selectedItemState =
                conversionListItemStateList.first { it.currency == selectedCurrency }
            val exchangeRate = selectedItemState.exchangeRate
            conversionListItemStateList.map {
                it.convertedValue = (exchangeRate / it.exchangeRate) * amountText.toDouble()
            }
            val availCurrencies = _currencyConverterLiveData.value?.availableCurrencyList

            availCurrencies?.let {
                val conversionListItemStates = ArrayList<BaseItemState>()
                for (index in availCurrencies.indices) {
                    conversionListItemStates.add(conversionListItemStateList[index])
                    conversionListItemStates.add(SeparatorItemState())
                }

                _currencyConverterLiveData.value =
                    CurrencyConverterViewState(availCurrencies, conversionListItemStates)
            }
        }
    }
}