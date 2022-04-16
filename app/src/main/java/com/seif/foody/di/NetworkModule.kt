package com.seif.foody.di

import com.seif.foody.utils.Constants.Companion.BASE_URL
import com.seif.foody.data.network.FoodRecipesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // we need to specify component -> so all this binding inside this network module will be available inside Application Components
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }
    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory{
        return GsonConverterFactory.create()
    }
    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
    @Singleton // application scope
    @Provides // we use Provides annotation bec we use retrofit library which is not created by us
    fun provideApiService(retrofit: Retrofit): FoodRecipesApi {
        return retrofit.create(FoodRecipesApi::class.java)
    }
}