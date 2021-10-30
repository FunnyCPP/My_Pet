package com.codart.animalnicknames.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.codart.animalnicknames.data.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    fun updateUser(user: User)
}