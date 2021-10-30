package com.codart.animalnicknames.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codart.animalnicknames.utils.Values
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var token: String = Values.TOKEN_BASIC,
    var isSubscribed: String = "false",
    var isLogged: String = "false"
)
