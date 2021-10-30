package com.codart.animalnicknames.data.repository

import com.codart.animalnicknames.data.entities.LikesRequest
import com.codart.animalnicknames.data.entities.User
import com.codart.animalnicknames.data.local.UserDao
import com.codart.animalnicknames.data.remote.NicknamesRemoteDataSource
import com.codart.animalnicknames.utils.performGetOperationWithoutDB
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val remoteDataSource: NicknamesRemoteDataSource,
    private val localDataSource: UserDao,
) {
    fun getUser() = localDataSource.getUser()

    fun updateUser(user: User) = localDataSource.updateUser(user)

    suspend fun addUser(user: User) =   localDataSource.insert(user)

    fun getToken() = performGetOperationWithoutDB { remoteDataSource.getToken() }

    fun getNicknamesWithFiltration( token: String, letter: String, gender: Int)= performGetOperationWithoutDB{remoteDataSource.getNicknamesWithFiltration(token, letter, gender)}

    fun getRandomNickname( token: String) = performGetOperationWithoutDB { remoteDataSource.getRandomNickname(token) }

    fun getRandomNicknameWithGenderFiltration( token: String, gender: Int) = performGetOperationWithoutDB { remoteDataSource.getRandomNicknameWithGenderFiltration(token,gender) }

    fun getTopNicknamesWithGenderFiltration( token: String, gender: Int)= performGetOperationWithoutDB{remoteDataSource.getTopNicknamesWithGenderFiltration(token, gender)}

    fun getWishlist(token: String) = performGetOperationWithoutDB { remoteDataSource.getWishList(token) }

    fun addNicknameToWishList(token: String, id: Int) = performGetOperationWithoutDB { remoteDataSource.addNicknameToWishList(token, id) }

    fun deleteNicknameFromWishList( token: String, id: Int) = performGetOperationWithoutDB { remoteDataSource.deleteNicknameFromWishList(token, id) }

    fun addLikeToNickname( token: String,  id: Int, androidId: String) = performGetOperationWithoutDB { remoteDataSource.addLikeToNickname(token, id, LikesRequest(androidId)) }

    fun addDislikeToNickname( token: String, id: Int, androidId: String) = performGetOperationWithoutDB { remoteDataSource.addDislikeToNickname(token, id, LikesRequest(androidId)) }

}