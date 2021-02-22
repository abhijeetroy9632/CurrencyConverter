package com.paypay.currencyconverter.ui.adapter.itemstate

import com.paypay.currencyconverter.ui.adapter.viewholder.CurrencyConverterViewType

data class ConversionListItemState(
        val currency: String,
        val exchangeRate: Double,
        var convertedValue: Double = 0.0
) : BaseItemState {
        override val viewType: CurrencyConverterViewType
                get() = CurrencyConverterViewType.EXCHANGE_RATE
}
