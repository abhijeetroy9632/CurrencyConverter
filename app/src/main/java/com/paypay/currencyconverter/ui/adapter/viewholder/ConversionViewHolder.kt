package com.paypay.currencyconverter.ui.adapter.viewholder

import androidx.databinding.ViewDataBinding
import com.paypay.currencyconverter.BR
import com.paypay.currencyconverter.ui.adapter.itemstate.ConversionListItemState
import com.paypay.currencyconverter.viewmodel.CurrencyConverterViewModel

class ConversionViewHolder(
    private val viewModel: CurrencyConverterViewModel,
    private val binding: ViewDataBinding
) : BaseViewHolder<ConversionListItemState>(binding) {

    override fun bindData(dataModel: ConversionListItemState) {
        with(binding) {
            setVariable(BR.currencyConverterViewModel, viewModel)
            setVariable(BR.conversionListItemState, dataModel)
            executePendingBindings()
        }
    }
}