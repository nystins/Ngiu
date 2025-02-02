package com.example.ngiu.data.entities

import androidx.room.*
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = TransactionType::class,
        parentColumns = arrayOf("trans_type_id"),
        childColumns = arrayOf("TypeID"),
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = MainCategories::class,
        parentColumns = arrayOf("main_cat_id"),
        childColumns = arrayOf("CategoryID"),
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = Account::class,
        parentColumns = arrayOf("acct_id"),
        childColumns = arrayOf("PayerID"),
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = Account::class,
        parentColumns = arrayOf("acct_id"),
        childColumns = arrayOf("RecipientID"),
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = Individual::class,
        parentColumns = arrayOf("indiv_id"),
        childColumns = arrayOf("IndividualID"),
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = Merchant::class,
        parentColumns = arrayOf("merchant_id"),
        childColumns = arrayOf("MerchantID"),
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = Project::class,
        parentColumns = arrayOf("project_id"),
        childColumns = arrayOf("ProjectID"),
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = Reimburse::class,
        parentColumns = arrayOf("reimburse_id"),
        childColumns = arrayOf("ReimburseID"),
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = Period::class,
        parentColumns = arrayOf("period_id"),
        childColumns = arrayOf("PeriodID"),
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE
    )
    ]
)

data class Transaction (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trans_id")
    val id: Int,
    val TypeID: Int,
    val CategoryID: Int,
    val PayerID: Int,
    val RecipientID: Int,
    val Amount: Double,
    @TypeConverters(DateTypeConverter::class)
    val Date: Date,
    val IndividualID: Int,
    val MerchantID: Int,
    val Memo: Char,
    val ProjectID: Int,
    val ReimburseID: Int,
    val PeriodID: Int
)