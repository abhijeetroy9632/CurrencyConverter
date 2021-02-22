package com.paypay.currencyconverter.ui.adapter.itemstate

import com.paypay.currencyconverter.ui.adapter.viewholder.CurrencyConverterViewType

interface BaseItemState {
    val viewType: CurrencyConverterViewType
    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int
}