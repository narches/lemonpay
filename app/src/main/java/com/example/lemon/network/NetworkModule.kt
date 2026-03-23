
package com.example.lemon.network


import android.content.Context
import com.example.lemon.session.SessionManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object ApiClient {

    private const val BASE_URL = "http://10.0.2.2:9000/"

    private lateinit var sessionManager: SessionManager

    fun init(context: Context) {
        sessionManager = SessionManager(context.applicationContext)
    }

    private val okHttpClient: OkHttpClient by lazy {
        check(::sessionManager.isInitialized) {
            "ApiClient.init(context) must be called before use"
        }

        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionManager))
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val dashboardAPI: DashboardAPI by lazy {
        retrofit.create(DashboardAPI::class.java)
    }

    val authAPI: AuthAPI by lazy {
        retrofit.create(AuthAPI::class.java)
    }
}