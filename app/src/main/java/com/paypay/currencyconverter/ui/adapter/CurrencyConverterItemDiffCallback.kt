package com.paypay.currencyconverter.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.paypay.currencyconverter.ui.adapter.itemstate.BaseItemState

class CurrencyConverterItemDiffCallback: DiffUtil.ItemCallback<BaseItemState>() {
    override fun areItemsTheSame(oldItem: BaseItemState, newItem: BaseItemState): Boolean {
        return  oldItem.viewType == newItem.viewType
    }

    override fun areContentsTheSame(oldItem: BaseItemState, newItem: BaseItemState): Boolean {
        return oldItem == newItem
    }
}