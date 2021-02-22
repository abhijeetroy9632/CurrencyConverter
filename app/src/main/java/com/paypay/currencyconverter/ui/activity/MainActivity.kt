package com.paypay.currencyconverter.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.paypay.currencyconverter.R
import com.paypay.currencyconverter.databinding.ActivityMainBinding
import com.paypay.currencyconverter.ui.adapter.ConverterListAdapter
import com.paypay.currencyconverter.viewmodel.CurrencyConverterViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CurrencyConverterViewModel

    private lateinit var binding: ActivityMainBinding
    private var converterListAdapter: ConverterListAdapter? = null
    private lateinit var selectedCurrency: String
    private lateinit var amountEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instantiate data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        amountEditText = binding.amountEditText
        // Get the view model instance from ViewModelProvider
        viewModel = ViewModelProvider(this).get(CurrencyConverterViewModel::class.java)

        with(binding) {
            currencyConverterViewModel = viewModel
            lifecycleOwner = this@MainActivity
        }
        setupListAdapter()
        getExchangeRates()
        setListeners()

    }

    /**
     * Calls view model to update live data
     */
    private fun getExchangeRates() {
        viewModel.fetchLiveExchangeRates()
        viewModel.currencyConverterLiveData.observe(this, {currencyConverterViewState ->
                converterListAdapter?.submitList(currencyConverterViewState.conversionListItemStateList)
                converterListAdapter?.notifyDataSetChanged()
            }
        )
    }

    /**
     * Configure list adapter
     */
    private fun setupListAdapter() {
        if(converterListAdapter == null) {
            converterListAdapter = ConverterListAdapter(viewModel)
        }
        with(binding.exchangeListView) {
            layoutManager = LinearLayoutManager(context)
            adapter = converterListAdapter
            setHasFixedSize(true)
        }
    }

    /**
     * Apply listeners to text view and spinner
     */
    private fun setListeners() {
        amountEditText.doOnTextChanged { text, _, _, _ ->
            if(!text.isNullOrBlank()) {
                viewModel.onCurrencyChanged(selectedCurrency, text.toString().trim())
            } else {
                amountEditText.setText(getString(R.string.default_amount))
            }
        }
        binding.currencyDropdown.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCurrency = parent?.selectedItem.toString()
                if(!amountEditText.text.isNullOrBlank()) {

                    viewModel.onCurrencyChanged(
                        selectedCurrency,
                        amountEditText.text.toString().trim()
                    )
                }
            }
        }
    }
}