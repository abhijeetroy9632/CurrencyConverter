package com.paypay.currencyconverter.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import com.paypay.currencyconverter.ui.adapter.itemstate.BaseItemState
import com.paypay.currencyconverter.ui.adapter.viewholder.BaseViewHolder
import com.paypay.currencyconverter.ui.adapter.viewholder.CurrencyConverterViewType
import com.paypay.currencyconverter.viewmodel.CurrencyConverterViewModel

class ConverterListAdapter(private val currencyConverterViewModel: CurrencyConverterViewModel)
    : ListAdapter<BaseItemState, BaseViewHolder<BaseItemState>>(
    AsyncDifferConfig.Builder(CurrencyConverterItemDiffCallback()).build()){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<BaseItemState> {
        return CurrencyConverterViewType.values()[viewType].createViewHolder(
            parent,
            currencyConverterViewModel)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BaseItemState>, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType.ordinal
    }
}