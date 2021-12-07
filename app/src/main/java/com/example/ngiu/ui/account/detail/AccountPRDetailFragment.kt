package com.example.ngiu.ui.account.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ngiu.MainActivity
import com.example.ngiu.R
import com.example.ngiu.databinding.FragmentAccountRecordsBinding
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_account_add_cash.*
import kotlinx.android.synthetic.main.fragment_account_records.*

class AccountPRDetailFragment : Fragment() {

    private lateinit var  AccountPRDetailViewModel:AccountPRDetailViewModel
    private var _binding: FragmentAccountRecordsBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var rvAccount: RecyclerView? = null
    private var adapter = AccountPRDetailAdapter()

    private var itemId: Long = 0
    private lateinit var accountName: String
    private var balance: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        AccountPRDetailViewModel =
            ViewModelProvider(this).get(AccountPRDetailViewModel::class.java)


        _binding = FragmentAccountRecordsBinding.inflate(inflater, container, false)
        rvAccount = binding.root.findViewById(R.id.rvAccountNormalDetails)


        fetchDataFromBundle()
        displayViews()


        return binding.root
    }

    private fun displayViews() {
        binding.tvAccountRecordName.text = accountName
        binding.tvAccountRecordBalance.text = balance.toString()

    }

    private fun fetchDataFromBundle() {
        itemId = arguments?.getLong("accountId")!!
        accountName = arguments?.getString("accountName")!!
        balance = arguments?.getDouble("balance")!!
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AccountPRDetailViewModel.getTransRecords(requireContext(),itemId, balance)
        rvAccount?.layoutManager = LinearLayoutManager(context)
        rvAccount?.adapter = adapter

        binding.tvInflowValue.text = AccountPRDetailViewModel.getInflow(requireContext(),itemId).toString()
        binding.tvOutflowValue.text = AccountPRDetailViewModel.getOutflow(requireContext(),itemId).toString()



            AccountPRDetailViewModel.accountRecordsList.observe(viewLifecycleOwner){
                adapter.addItems(it)
        }

        // set up toolbar icon and click event
        // choose items to show

        toolbar_account_normal_details.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        toolbar_account_normal_details.menu.findItem(R.id.action_edit).isVisible = true
        toolbar_account_normal_details.menu.findItem(R.id.action_add).isVisible = true

        // menu item clicked
        toolbar_account_normal_details.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.action_add -> {
                    // hide nav bottom bar
                    (activity as MainActivity).setNavBottomBarVisibility(View.GONE)
                    // navigate to add record screen
                    view.findNavController().navigate(R.id.navigation_record)
                    true
                }
                R.id.action_edit -> {
                    (activity as MainActivity).setNavBottomBarVisibility(View.GONE)
                    // navigate to add record screen
                    view.findNavController().navigate(R.id.navigation_record)
                    true
                }

                else -> super.onOptionsItemSelected(it)
            }
        }


    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

