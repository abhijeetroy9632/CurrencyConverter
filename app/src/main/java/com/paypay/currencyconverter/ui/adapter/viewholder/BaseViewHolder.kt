package com.paypay.currencyconverter.ui.adapter.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun bindData(dataModel: T)
}