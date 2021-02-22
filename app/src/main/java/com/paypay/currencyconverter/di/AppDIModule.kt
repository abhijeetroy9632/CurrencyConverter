package com.paypay.currencyconverter.di

//import androidx.lifecycle.ViewModel
//import com.paypay.currencyconverter.network.RetrofitApiClientModule
//import com.paypay.currencyconverter.repository.CurrencyConverterRepository
//import com.paypay.currencyconverter.viewmodel.CurrencyConverterViewModel
//import dagger.Module
//import dagger.Provides
//import dagger.multibindings.IntoMap
//import javax.inject.Provider
//
//@Module //(includes = [RetrofitApiClientModule::class])
//class AppDIModule {
//
//    @Provides
//    fun provideViewModelFactory(viewModels: Map<Class<out ViewModel>, Provider<ViewModel>>): ViewModelFactory {
//        return ViewModelFactory.create(viewModels)
//    }
//
//    @Provides
//    @IntoMap
//    @ViewModelKey(CurrencyConverterViewModel::class)
//    fun provideCurrencyConverterViewModel(
//        currencyConverterRepository: CurrencyConverterRepository
//    ): ViewModel {
//        return CurrencyConverterViewModel.create(currencyConverterRepository)
//    }
//
//    @Provides
//    fun provideCurrencyConverterRepository(retrofitApiClient: RetrofitApiClientModule): CurrencyConverterRepository {
//        return CurrencyConverterRepository(retrofitApiClient)
//    }
//
//    @Provides
//    fun provideRetrofitApiClientModule(): RetrofitApiClientModule {
//        return RetrofitApiClientModule()
//    }
//}