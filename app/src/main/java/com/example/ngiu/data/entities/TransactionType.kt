package com.example.ngiu.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["TransactionType_ID"], unique = true)])
data class TransactionType (
    @PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "ID")
    val TransactionType_ID: Long,
    //@ColumnInfo(name = "Name")
    val TransactionType_Name: String
)
