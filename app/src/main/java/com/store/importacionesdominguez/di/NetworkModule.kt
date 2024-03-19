package com.store.importacionesdominguez.di

import android.content.Context
import com.store.importacionesdominguez.utils.preferences.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.3:8099/api/dominguez/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    // OkHttpClient
    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    // Interceptor para agregar el token de autenticación
    @Provides
    @Singleton
    fun provideInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            val token = SharedPreferences.getToken(context)
            token?.let {
                val original = chain.request()
                val requestBuilder =
                    original.newBuilder()
                        .header("Authorization", "Bearer $token")
                val request = requestBuilder.build()
                chain.proceed(request)
            } ?: chain.proceed(chain.request())
        }
    }
}