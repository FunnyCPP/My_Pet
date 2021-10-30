package com.codart.animalnicknames.ui

import androidx.lifecycle.ViewModel
import com.codart.animalnicknames.data.entities.User
import com.codart.animalnicknames.data.repository.AppRepository
import com.codart.animalnicknames.utils.Values
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AppRepository
):ViewModel() {
    fun setViewModelUser(user: User){ MainViewModel.user =user}

    fun getViewModelUser()= user

    fun getUser() = repository.getUser()

    fun updateUser(user: User) = repository.updateUser(user)

    suspend fun addUser(user: User) =  repository.addUser(user)

    fun getToken() =  repository.getToken()

    fun getNicknamesWithFiltration( token: String, letter: String, gender: Int)= repository.getNicknamesWithFiltration(token, letter, gender)

    fun getRandomNickname( token: String) =   repository.getRandomNickname(token)

    fun getRandomNicknameWithGenderFiltration( token: String, gender: Int) =   repository.getRandomNicknameWithGenderFiltration(token,gender)

    fun getTopNicknamesWithGenderFiltration( token: String, gender: Int)= repository.getTopNicknamesWithGenderFiltration(token, gender)

    fun getWishlist(token: String)= repository.getWishlist(token)

    fun addNicknameToWishList(token: String, id: Int) =   repository.addNicknameToWishList(token, id)

    fun deleteNicknameFromWishList( token: String, id: Int) =   repository.deleteNicknameFromWishList(token, id)

    fun addLikeToNickname( token: String,  id: Int, androidId:String) =  repository.addLikeToNickname(token, id, androidId)

    fun addDislikeToNickname( token: String, id: Int, androidId:String) =   repository.addDislikeToNickname(token, id,androidId)

    companion object{
        private var user = User(0,Values.TOKEN_BASIC,"false","false")
    }
}