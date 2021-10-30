package com.codart.animalnicknames.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codart.animalnicknames.data.entities.User
import kotlinx.coroutines.runBlocking

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract  fun userDao(): UserDao
    companion object{
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this){ instance ?: buildDatabase(context).also{ instance = it}}
        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "appdb")
                .fallbackToDestructiveMigration()
                .createFromAsset("data.json")
                .build()
    }
}