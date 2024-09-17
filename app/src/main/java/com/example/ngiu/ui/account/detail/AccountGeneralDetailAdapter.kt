package com.example.ngiu.ui.account.detail

import android.annotation.SuppressLint
import android.icu.text.MessageFormat.format
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ngiu.R
import com.example.ngiu.data.entities.Account
import com.example.ngiu.data.entities.Trans
import com.example.ngiu.data.entities.returntype.RecordDetail
import com.example.ngiu.data.entities.returntype.TransactionDetail
import com.example.ngiu.ui.account.model.AccountTransRecordModel
import kotlinx.android.synthetic.main.cardview_account_general_detail_item.view.*
import kotlinx.android.synthetic.main.cardview_account_p_r_detail_item.view.*
import kotlinx.android.synthetic.main.cardview_account_records.view.*
import kotlinx.android.synthetic.main.cardview_transaction.view.*
import org.w3c.dom.Text
import java.lang.String.format
import java.text.MessageFormat.format
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AccountGeneralDetailAdapter(
    private val onClickListener: OnClickListener
    )
    : RecyclerView.Adapter<AccountGeneralDetailAdapter.ViewHolder>() {

    private var listDetail: List<RecordDetail> = ArrayList()
    private var totalAccountBalance: Double = 0.00
    private var currentAccountID: Long = 0L
    private val recordTimeFormatter = DateTimeFormatter.ofPattern("hh:mm")
    private val groupDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy EEEE")

    // interface for passing the onClick event to fragment.
    interface OnClickListener {
        fun onItemClick(transID: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the custom view from xml layout file
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_account_general_detail_item,parent,false)


        // return the view holder
        return ViewHolder(view)

    }


    //@SuppressLint("ResourceAsColor")
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // display the custom class
        listDetail[position].apply {
            holder.recordTime.text = Transaction_Date.format(recordTimeFormatter)

            // category, amount , info
            when (TransactionType_ID){
                1L -> {
                    // category
                    holder.recordText.text = SubCategory_Name
                    // amount
                    holder.recordAmount.text = "$-" + "%.2f".format(Transaction_Amount)
                    holder.recordAmount.setTextColor(holder.expenseColor)
                    // info
                    holder.recordInfo.visibility = View.GONE
                }
                2L -> {
                    // category
                    holder.recordText.text = SubCategory_Name
                    // amount
                    holder.recordAmount.text = "$" + "%.2f".format(Transaction_Amount)
                    holder.recordAmount.setTextColor(holder.incomeColor)
                    // info
                    holder.recordInfo.visibility = View.GONE
                }
                3L,4L ->{
                    // info
                    holder.recordInfo.visibility = View.VISIBLE

                    // category
                    if (TransactionType_ID == 3L){
                        if (Account_ID == currentAccountID){
                            holder.recordText.text = holder.itemView.context.getString(R.string.record_transfer_out)
                        }else{
                            holder.recordText.text = holder.itemView.context.getString(R.string.record_transfer_in)
                        }
                        // info
                        holder.recordInfo.text = "$Account_Name " + holder.itemView.context.getString(R.string.record_to) + " $AccountRecipient_Name"
                    }
                    if (TransactionType_ID == 4L){
                        when (SubCategory_ID){
                            7L -> {
                                holder.recordText.text = holder.itemView.context.getString(R.string.record_borrow_in)
                                holder.recordInfo.text = holder.itemView.context.getString(R.string.record_borrow_from) + " $Account_Name"
                            }
                            8L -> {
                                holder.recordText.text = holder.itemView.context.getString(R.string.record_lend_out)
                                holder.recordInfo.text = holder.itemView.context.getString(R.string.record_lend_to) + " $AccountRecipient_Name"
                            }
                            9L -> {
                                holder.recordText.text = holder.itemView.context.getString(R.string.record_repay)
                                holder.recordInfo.text = holder.itemView.context.getString(R.string.record_paid_to) + " $AccountRecipient_Name"
                            }
                            10L -> {
                                holder.recordText.text = holder.itemView.context.getString(R.string.record_receive)
                                holder.recordInfo.text = holder.itemView.context.getString(R.string.record_received_from) + " $Account_Name"
                            }
                        }
                    }
                    // amount
                    holder.recordAmount.text = "$" + "%.2f".format(Transaction_Amount)
                    holder.recordAmount.setTextColor(holder.amountColor)

                }
            }


            holder.recordMemo.text = Transaction_Memo


            if (position == 0){
                // date
                holder.groupLayout.visibility = View.VISIBLE
                holder.groupDate.text = Transaction_Date.format(groupDateFormatter)
                // balance
                holder.recordBalance.text = "$" + "%.2f".format(totalAccountBalance)

            }else{
                // date
                holder.groupDate.text = Transaction_Date.format(groupDateFormatter)

                if (holder.groupDate.text == listDetail[position-1].Transaction_Date.format(groupDateFormatter)) {
                    holder.groupLayout.visibility = View.GONE
                }else{
                    holder.groupLayout.visibility = View.VISIBLE
                }
                // balance
                when (listDetail[position - 1].TransactionType_ID){
                    1L -> totalAccountBalance += listDetail[position - 1].Transaction_Amount
                    2L -> totalAccountBalance -= listDetail[position - 1].Transaction_Amount
                    3L, 4L -> {
                        if (Account_ID == currentAccountID){
                            totalAccountBalance += listDetail[position - 1].Transaction_Amount
                        }else{
                            totalAccountBalance -= listDetail[position - 1].Transaction_Amount
                        }
                    }
                }

                holder.recordBalance.text = "$" + "%.2f".format(totalAccountBalance)

            }

            holder.itemLayout.setOnClickListener {
                onClickListener.onItemClick(Transaction_ID)
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<RecordDetail>){
        listDetail = list
        notifyDataSetChanged()
    }

    fun setTotalAccountBalance(acctID: Long, double: Double){
        totalAccountBalance = double
        currentAccountID = acctID
    }


    override fun getItemCount(): Int {
        // the data set held by the adapter.
        return listDetail.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val groupDate: TextView = itemView.tv_account_general_item_group_date
        val recordText: TextView = itemView.tv_account_general_item_text
        val recordTime: TextView = itemView.tv_account_general_item_time
        val recordMemo: TextView = itemView.tv_account_general_item_memo
        val recordInfo: TextView = itemView.tv_account_general_item_info
        val recordAmount: TextView = itemView.tv_account_general_item_amount
        val recordBalance: TextView = itemView.tv_account_general_item_balance

        val groupLayout: ConstraintLayout = itemView.layout_account_general_card_view_group
        val itemLayout: ConstraintLayout = itemView.layout_account_general_card_view_item

        val expenseColor = ContextCompat.getColor(itemView.context, R.color.app_expense_amount)
        val incomeColor = ContextCompat.getColor(itemView.context, R.color.app_income_amount)
        val amountColor = ContextCompat.getColor(itemView.context, R.color.app_amount)

    }


    // this two methods useful for avoiding duplicate item
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}