package com.paypay.currencyconverter.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.paypay.currencyconverter.databinding.ConversionListItemBinding
import com.paypay.currencyconverter.databinding.SeparatorViewBinding
import com.paypay.currencyconverter.ui.adapter.itemstate.BaseItemState
import com.paypay.currencyconverter.viewmodel.CurrencyConverterViewModel

@Suppress("UNCHECKED_CAST")
enum class CurrencyConverterViewType {

    EXCHANGE_RATE {
        override fun createViewHolder(
            parent: ViewGroup,
            currencyConverterViewModel: CurrencyConverterViewModel
        ): BaseViewHolder<BaseItemState> {
            return ConversionViewHolder(
                currencyConverterViewModel,
                ConversionListItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            ) as BaseViewHolder<BaseItemState>
        }
    },

    SEPARATOR {
        override fun createViewHolder(
            parent: ViewGroup,
            currencyConverterViewModel: CurrencyConverterViewModel
        ): BaseViewHolder<BaseItemState> {
            return SeparatorViewHolder(
                SeparatorViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            ) as BaseViewHolder<BaseItemState>
        }
    };
    abstract fun createViewHolder(
            parent: ViewGroup,
            currencyConverterViewModel: CurrencyConverterViewModel
    ): BaseViewHolder<BaseItemState>
}