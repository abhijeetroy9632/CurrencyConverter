package com.paypay.currencyconverter.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel() {
    private val disposables = CompositeDisposable()

    fun launchJob(job: Disposable) {
        disposables.add(job)
    }
    fun launchJobs(vararg jobs: Disposable) {
        disposables.addAll(*jobs)
    }
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}