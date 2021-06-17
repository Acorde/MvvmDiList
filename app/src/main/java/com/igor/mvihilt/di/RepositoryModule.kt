package com.igor.mvihilt.di

import com.igor.mvihilt.network.RestApi
import com.igor.mvihilt.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(restApi : RestApi) : Repository{
        return Repository(restApi)
    }

}