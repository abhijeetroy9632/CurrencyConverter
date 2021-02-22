package com.paypay.currencyconverter

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.paypay.currencyconverter.model.LiveExchangeRatesEntity
import com.paypay.currencyconverter.network.RetrofitApiService
import com.paypay.currencyconverter.repository.CurrencyConverterRepository
import com.paypay.currencyconverter.ui.adapter.itemstate.ConversionListItemState
import com.paypay.currencyconverter.ui.adapter.viewholder.CurrencyConverterViewType
import com.paypay.currencyconverter.viewmodel.CurrencyConverterViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Single
import org.junit.*

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

//@RunWith(MockitoJUnitRunner::class)
class CurrencyConverterViewModelTest {

    // A JUnit Test Rule that swaps the background executor used by
    // the Architecture Components with a different one which executes each task synchronously.
    // You can use this rule for your host side tests that use Architecture Components.
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    // Test rule for making the RxJava to run synchronously in unit test
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    private lateinit var viewModel: CurrencyConverterViewModel
    private lateinit var single: Single<LiveExchangeRatesEntity>

    private lateinit var context: Context

    @RelaxedMockK
    private lateinit var repository: CurrencyConverterRepository



    @Before
    fun setUp() {
        // initialize the ViewModel with a mocked github api
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = CurrencyConverterViewModel()
        context = mock(Context::class.java)
        ApplicationContextUtil.initAppContext(context)
    }

    @Test
    fun `given - data is needed, when - fetching data, then - success`() {

        val liveExchangeRatesEntity = TestDataUtil.getExchangeRatesResponse()
        single = Single.just(liveExchangeRatesEntity)

        every { repository.fetchLiveExchangeRates() } returns single

        viewModel.fetchLiveExchangeRates()

        assertEquals(169, viewModel.currencyConverterLiveData.value?.availableCurrencyList?.size)

    }

    @Test
    fun `given - after fetching data, when - raw data is available, then - convert it to view state`() {
        val viewState = viewModel.convertToViewState(TestDataUtil.getExchangeRatesResponse())
        assertEquals(CurrencyConverterViewType.EXCHANGE_RATE, viewState.conversionListItemStateList.get(0).viewType)
    }
}