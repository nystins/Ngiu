package com.example.ngiu.ui.account.addaccounts

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.ngiu.R
import com.example.ngiu.data.entities.Account
import com.example.ngiu.data.entities.Currency
import com.example.ngiu.databinding.FragmentAddCashBinding
import com.example.ngiu.functions.addDecimalLimiter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.popup_title.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddCashFragment : Fragment() {
    private var _binding: FragmentAddCashBinding? = null
    private val binding get() = _binding!!
    var currency = "USD"


    private val viewModel: AddCashViewModel by lazy {
        ViewModelProvider(this).get(AddCashViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentAddCashBinding.inflate(inflater, container, false).run {
            _binding = this
            root
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        getView()?.findViewById<TextInputEditText?>(R.id.tetCashBalance)?.addDecimalLimiter()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initListeners() {
        binding.btnSaveCash.setOnClickListener {
            submitForm()
        }

        binding.btnCashAddOtherCurrency.setOnClickListener {

            val array : List<Currency> = viewModel.getCurrency(requireActivity())

            val arrayList: ArrayList<String> = ArrayList()

            for (item in array){
                arrayList.add(item.Currency_ID)
            }

            val cs: Array<CharSequence> = arrayList.toArray(arrayOfNulls<CharSequence>(arrayList.size))

            //   val array = arrayof()
            // Initialize a new instance of alert dialog builder object
            val builder = AlertDialog.Builder(requireContext())

            // set Title Style
            val titleView = layoutInflater.inflate(R.layout.popup_title,null)
            // set Title Text
            titleView.tv_popup_title_text.text = getText(R.string.option_title_add_currency)

            builder.setCustomTitle(titleView)

            // Set items form alert dialog
            builder.setItems(cs) { _, which ->
                currency = arrayList.get(which)
                binding.cashBalanceTextLayout.setSuffixText(currency)

            }

            // Create a new AlertDialog using builder object
            // Finally, display the alert dialog
            builder.create().show()
        }
    }

    private fun insertData() {
            val accountName = binding.tetCashAccountName.text.toString()
            val countInNetAsset: Boolean = binding.scCashCountNetAssets.isChecked
            val memo = binding.tetCashMemo.text.toString()
            val accountTypeID = 1L
            var balance = binding.tetCashBalance.text.toString()

            if (balance.isEmpty()) {
                balance = "0.0"
            }
            val cashAccount = Account(
                Account_Name =  accountName, Account_Balance =  balance.toDouble(),
                Account_CountInNetAssets =  countInNetAsset, Account_Memo = memo, AccountType_ID = accountTypeID, Currency_ID = currency)

            GlobalScope.launch{
                viewModel.insertCash(requireActivity(), cashAccount)
            }

    }

    private fun submitForm() {
        binding.cashAccountNameTextLayout.helperText = validAccountName()
        binding.cashBalanceTextLayout.helperText = validBalance()

        val validAccountName = binding.cashAccountNameTextLayout.helperText == null
        val validBalance = binding.cashBalanceTextLayout.helperText == null

        if (validAccountName && validBalance ) {
            insertData()
            requireActivity().onBackPressed()
        }
        else
            invalidForm()
    }

    private fun invalidForm() {
        var message = ""
        if(binding.cashAccountNameTextLayout.helperText != null) {
            message += "\nAccountName: " + binding.cashAccountNameTextLayout.helperText
        }
        if(binding.cashBalanceTextLayout.helperText != null) {
            message += "\n\nBalance: " + binding.cashBalanceTextLayout.helperText
        }

        AlertDialog.Builder(context)
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("Okay"){ _,_ ->
                // do nothing
            }
            .show()
    }


    private fun validAccountName(): String? {
        val accountNameText = binding.tetCashAccountName.text.toString()
        if(accountNameText.length < 3) {
            return "Minimum of 3 Characters required."
        }
       return null
    }

    private fun validBalance(): String? {
        val accountBalanceText = binding.tetCashBalance.text.toString()
        if(accountBalanceText.length < 0) {
            return "Invalid Balance Entry."
        }
        return null
    }



}