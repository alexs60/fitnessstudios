package com.alessandrofarandagancio.fitnessstudios.di

import com.alessandrofarandagancio.fitnessstudios.BuildConfig
import com.alessandrofarandagancio.fitnessstudios.api.google.DirectionApiHelper
import com.alessandrofarandagancio.fitnessstudios.api.google.DirectionApiHelperImpl
import com.alessandrofarandagancio.fitnessstudios.api.google.DirectionApiService
import com.alessandrofarandagancio.fitnessstudios.api.yelp.ApiHelper
import com.alessandrofarandagancio.fitnessstudios.api.yelp.ApiHelperImpl
import com.alessandrofarandagancio.fitnessstudios.api.yelp.ApiService
import com.alessandrofarandagancio.fitnessstudios.constant.baseMapsRestApiUrl
import com.alessandrofarandagancio.fitnessstudios.constant.baseYelpRestApiUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("base_yelp")
    fun provideYelpBaseUrl() = baseYelpRestApiUrl

    @Provides
    @Named("base_maps")
    fun provideMapsBaseUrl() = baseMapsRestApiUrl

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    @Named("retrofit_yelp")
    fun provideYelpRetrofit(
        okHttpClient: OkHttpClient,
        @Named("base_yelp") yelpBaseUrl: String
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(yelpBaseUrl)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @Named("retrofit_maps")
    fun provideMapsRetrofit(
        okHttpClient: OkHttpClient,
        @Named("base_maps") mapsBaseUrl: String
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(mapsBaseUrl)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(@Named("retrofit_yelp") retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper


    @Singleton
    @Provides
    fun provideDirectionApiService(@Named("retrofit_maps") retrofit: Retrofit): DirectionApiService =
        retrofit.create(DirectionApiService::class.java)

    @Singleton
    @Provides
    fun provideDirectionApiHelper(directionApiHelperImpl: DirectionApiHelperImpl): DirectionApiHelper =
        directionApiHelperImpl


}