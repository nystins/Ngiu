package com.example.ngiu.data.entities

import androidx.room.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = AccountType::class,
        parentColumns = ["AccountType_ID"],
        childColumns = ["AccountType_ID"],
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = Currency::class,
        parentColumns = ["Currency_ID"],
        childColumns = ["Currency_ID"],
        onDelete = ForeignKey.SET_DEFAULT,
        onUpdate = ForeignKey.CASCADE,
    )], indices = [Index(value = ["Account_ID"], unique = true)]
)

data class Account(
    @PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "ID")
    val Account_ID: Long = 0,

    @ColumnInfo(defaultValue = "1")
    val AccountType_ID: Long = 1,

    @ColumnInfo(defaultValue = "")
    val Account_Name: String = "",

    // balance | current arrears | value
    @ColumnInfo(defaultValue = "0.00")
    var Account_Balance: Double = 0.00,

    @ColumnInfo(defaultValue = "true")
    val Account_CountInNetAssets: Boolean = true,

    @ColumnInfo(defaultValue = "")
    val Account_Memo: String = "",

    @ColumnInfo(defaultValue = "USD")
    val Currency_ID: String = "USD",

    //credit card part -------

    //card number | user ID
    @ColumnInfo(defaultValue = "")
    val Account_CardNumber: String = "",

    @ColumnInfo(defaultValue = "1")
    val Account_StatementDay: Int = 1,

    @ColumnInfo(defaultValue = "0.00")
    val Account_CreditLimit: Double = 0.00,

    @ColumnInfo(defaultValue = "true")
    val Account_FixedPaymentDay: Boolean = true,

    @ColumnInfo(defaultValue = "25")
    val Account_PaymentDay: Int = 25,

    @ColumnInfo(defaultValue = "25")
    val Account_Interval: Int = 25,

    @ColumnInfo(defaultValue = "0")
    val Account_Reminder: Int = 0,

    @ColumnInfo(defaultValue = "0")
    val Account_StatisticalMode: Int = 0

)