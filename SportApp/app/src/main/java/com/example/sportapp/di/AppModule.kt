package com.example.sportapp.di

import com.example.sportapp.CONSTS.BASE_URL
import com.example.sportapp.network.AppApi
import com.example.sportapp.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun creteApi(): AppApi {
        return Retrofit.Builder().baseUrl(BASE_URL).client(
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("X-RapidAPI-Key", "7709136e1amsh4dc7e776b506c8ep1eddc0jsn6af49b8e99c4")
                        .addHeader("X-RapidAPI-Host", "football-prediction-api.p.rapidapi.com")
                        .build()
                    chain.proceed(request)
                }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository()= AppRepository(creteApi())

}