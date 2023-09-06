package com.example.data.module

import com.example.data.BuildConfig
import com.example.data.network.service.BookingService
import com.example.data.network.service.HotelService
import com.example.data.network.service.RoomsService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideBookingService(
        retrofit: Retrofit
    ): BookingService {
        return retrofit.create(BookingService::class.java)
    }

    @Singleton
    @Provides
    fun provideRoomService(
        retrofit: Retrofit
    ): RoomsService {
        return retrofit.create(RoomsService::class.java)
    }

    @Singleton
    @Provides
    fun provideHotelService(
        retrofit: Retrofit
    ): HotelService {
        return retrofit.create(HotelService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(
        gson: Gson
    ): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }
}