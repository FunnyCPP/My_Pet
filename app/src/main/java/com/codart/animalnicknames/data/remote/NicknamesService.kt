package com.codart.animalnicknames.data.remote

import com.codart.animalnicknames.data.entities.LikesRequest
import com.codart.animalnicknames.data.entities.LikesResponse
import com.codart.animalnicknames.data.entities.NicknamesResponse
import com.codart.animalnicknames.data.entities.TokenResponse
import com.codart.animalnicknames.utils.Values
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface NicknamesService {


    @POST(Values.PATH_GET_TOKEN)
    suspend fun getToken(@Header("Authorization") token: String = Values.TOKEN_BASIC): Response<TokenResponse>

    @GET("petname/name/{letter}/gender/{gender}/limit/1000/page/1")
    suspend fun getNicknamesWithFiltration(@Header("Authorization") token: String,@Path("letter") letter: String,@Path("gender") gender: Int, @Header("X-Oc-Merchant-Language") locale: String = Values.locale): Response<NicknamesResponse>

    @GET("petrandomname/gender/0")
    suspend fun getRandomNickname(@Header("Authorization") token: String, @Header("X-Oc-Merchant-Language") locale: String = Values.locale): Response<NicknamesResponse>

    @GET("petrandomname/gender/{gender}")
    suspend fun getRandomNicknameWithGenderFiltration(@Header("Authorization") token: String, @Path("gender") gender: Int, @Header("X-Oc-Merchant-Language") locale: String = Values.locale): Response<NicknamesResponse>

    @GET("pettopname/gender/{gender}")
    suspend fun getTopNicknamesWithGenderFiltration(@Header("Authorization") token: String, @Path("gender") gender: Int, @Header("X-Oc-Merchant-Language") locale: String = Values.locale): Response<NicknamesResponse>

    @POST("namewishlist/{id}")
    suspend fun addNicknameToWishList(@Header("Authorization") token: String, @Path("id") id: Int, @Header("X-Oc-Merchant-Language") locale: String = Values.locale): Response<NicknamesResponse>

    @DELETE("namewishlist/{id}")
    suspend fun deleteNicknameFromWishList(@Header("Authorization") token: String, @Path("id") id: Int, @Header("X-Oc-Merchant-Language") locale: String = Values.locale): Response<NicknamesResponse>

    @GET("namewishlist")
    suspend fun getWishList(@Header("Authorization") token: String): Response<NicknamesResponse>

    @POST("likename/{id}")
    suspend fun addLikeToNickname(@Header("Authorization") token: String, @Path("id") id: Int,@Body likesRequest: LikesRequest, @Header("X-Oc-Merchant-Language") locale: String = Values.locale): Response<LikesResponse>

    @POST("dislikename/{id}")
    suspend fun addDislikeToNickname(@Header("Authorization") token: String, @Path("id") id: Int,@Body likesRequest: LikesRequest, @Header("X-Oc-Merchant-Language") locale: String = Values.locale): Response<LikesResponse>
}