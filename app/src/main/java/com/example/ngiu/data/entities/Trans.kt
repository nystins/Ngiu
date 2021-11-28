package com.example.ngiu.data.entities



import androidx.room.*
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = TransactionType::class,
        parentColumns = ["TransactionType_ID"],
        childColumns = ["TransactionType_ID"],
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = SubCategory::class,
        parentColumns = ["SubCategory_ID"],
        childColumns = ["SubCategory_ID"],
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = Account::class,
        parentColumns = ["Account_ID"],
        childColumns = ["Account_ID"],
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = Account::class,
        parentColumns = ["Account_ID"],
        childColumns = ["AccountRecipient_ID"],
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = Person::class,
        parentColumns = ["Person_ID"],
        childColumns = ["Person_ID"],
        onDelete = ForeignKey.SET_DEFAULT,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = Merchant::class,
        parentColumns = ["Merchant_ID"],
        childColumns = ["Merchant_ID"],
        onDelete = ForeignKey.SET_DEFAULT,
        onUpdate = ForeignKey.CASCADE
    ),ForeignKey(
        entity = Project::class,
        parentColumns = ["Project_ID"],
        childColumns = arrayOf("Project_ID"),
        onDelete = ForeignKey.SET_DEFAULT,
        onUpdate = ForeignKey.CASCADE
    )
    ], indices = [Index(value = ["Transaction_ID"], unique = true)]
)

data class Trans(
    @PrimaryKey(autoGenerate = true)
    var Transaction_ID: Long = 0,
    @ColumnInfo(defaultValue = "1")
    var TransactionType_ID: Long = 1L,
    @ColumnInfo(defaultValue = "1")
    var SubCategory_ID: Long = 1L,
    @ColumnInfo(defaultValue = "1")
    var Account_ID: Long = 1L,
    @ColumnInfo(defaultValue = "1")
    var AccountRecipient_ID: Long = 1L,
    @ColumnInfo(defaultValue = "0.00")
    var Transaction_Amount: Double = 0.00,
    @TypeConverters(DateTypeConverter::class)
    var Transaction_Date: Date = Date(),
    @ColumnInfo(defaultValue = "1")
    var Person_ID: Long = 1L,
    @ColumnInfo(defaultValue = "1")
    var Merchant_ID: Long = 1L,
    @ColumnInfo(defaultValue = "")
    var Transaction_Memo: String = "",
    @ColumnInfo(defaultValue = "1")
    var Project_ID: Long = 1,
    @ColumnInfo(defaultValue = "0")
    var Transaction_ReimburseStatus: Int = 0,
    @ColumnInfo(defaultValue = "0")
    var Period_ID: Long = 0
){
    /*
    constructor( ): this(
        0,
        1L,
        1L,
        1L,
        1L,
        0.00,
        Date(),
        1L,
        1L,
        "",
        1L,
        0,
        0)

     */
}
