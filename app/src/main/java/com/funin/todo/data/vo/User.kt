package com.funin.todo.data.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey @ColumnInfo(name = "user_id") @SerializedName("id") val id: Int?,
    @ColumnInfo(name = "nickname") val nickname: String?,
    @ColumnInfo(name = "user_profile_url") val profileImage: String? = null
)