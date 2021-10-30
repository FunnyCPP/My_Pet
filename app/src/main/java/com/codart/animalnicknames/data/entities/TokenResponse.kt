package com.codart.animalnicknames.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

data class TokenResponse(
    val success: Int,
    val error: Array<String>,
    val data: Token
)
data class Token(
    val access_token: String,
    val expires_in: Int,
    val token_type: String
)