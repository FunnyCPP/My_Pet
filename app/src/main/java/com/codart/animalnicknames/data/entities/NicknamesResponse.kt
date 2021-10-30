package com.codart.animalnicknames.data.entities

data class NicknamesResponse(
    var data: List<Nickname>,
    var error: List<Any>,
    var success: Int
)

data class Nickname(
    var gender: String,
    var id: Int,
    var name: String,
    var rating: Int,
    var wishlist: String
)