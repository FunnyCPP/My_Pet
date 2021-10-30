package com.codart.animalnicknames.di.module

import android.content.Context
import com.codart.animalnicknames.data.local.AppDatabase
import com.codart.animalnicknames.data.local.UserDao
import com.codart.animalnicknames.data.remote.NicknamesRemoteDataSource
import com.codart.animalnicknames.data.remote.NicknamesService
import com.codart.animalnicknames.data.repository.AppRepository
import com.codart.animalnicknames.utils.Values
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(Values.PATH_BASE)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()


    @Provides
    fun provideNicknamesService(retrofit: Retrofit): NicknamesService = retrofit.create(NicknamesService::class.java)

    @Singleton
    @Provides
    fun provideAuthRemoteDataSource(nicknamesService: NicknamesService)= NicknamesRemoteDataSource(nicknamesService)

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideAppRepository(remoteDataSource: NicknamesRemoteDataSource, localDataSource: UserDao) = AppRepository(remoteDataSource, localDataSource)

}