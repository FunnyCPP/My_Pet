package com.codart.animalnicknames.data.remote

import com.codart.animalnicknames.data.entities.LikesRequest
import com.codart.animalnicknames.utils.Values
import retrofit2.http.*
import javax.inject.Inject

class NicknamesRemoteDataSource @Inject constructor(
    private val service: NicknamesService
):BaseDataSource() {

    suspend fun getToken(@Header("Authorization") token: String = Values.TOKEN_BASIC) = getResult { service.getToken() }


    suspend fun getNicknamesWithFiltration( token: String, letter: String, gender: Int) = getResult { service.getNicknamesWithFiltration(token, letter, gender) }


    suspend fun getRandomNickname( token: String) = getResult { service.getRandomNickname(token) }


    suspend fun getRandomNicknameWithGenderFiltration( token: String, gender: Int) = getResult { service.getRandomNicknameWithGenderFiltration(token, gender) }


    suspend fun getTopNicknamesWithGenderFiltration( token: String, gender: Int) = getResult { service.getTopNicknamesWithGenderFiltration(token, gender) }


    suspend fun addNicknameToWishList(token: String, id: Int) = getResult { service.addNicknameToWishList(token,id) }


    suspend fun deleteNicknameFromWishList( token: String, id: Int) = getResult { service.deleteNicknameFromWishList(token, id) }


    suspend fun getWishList(token: String) = getResult { service.getWishList(token) }


    suspend fun addLikeToNickname( token: String,  id: Int, likesRequest: LikesRequest) = getResult { service.addLikeToNickname(token, id, likesRequest) }

    suspend fun addDislikeToNickname( token: String, id: Int, likesRequest: LikesRequest) = getResult { service.addDislikeToNickname(token, id, likesRequest) }
}