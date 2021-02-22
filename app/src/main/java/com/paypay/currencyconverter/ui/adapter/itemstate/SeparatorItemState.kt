package com.paypay.currencyconverter.ui.adapter.itemstate

import com.paypay.currencyconverter.ui.adapter.viewholder.CurrencyConverterViewType

data class SeparatorItemState(
    override val viewType: CurrencyConverterViewType = CurrencyConverterViewType.SEPARATOR
) : BaseItemState
