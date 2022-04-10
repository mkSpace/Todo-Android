package com.funin.todo.data.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "me")
data class Me(@PrimaryKey @ColumnInfo(name = "user_id") @SerializedName("user_id") val userId: Int)