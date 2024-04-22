package com.gerrysatria.githubuser.core.di

import androidx.room.Room
import com.gerrysatria.githubuser.core.BuildConfig
import com.gerrysatria.githubuser.core.data.UserRepository
import com.gerrysatria.githubuser.core.data.source.local.LocalDataSource
import com.gerrysatria.githubuser.core.data.source.local.room.UserDatabase
import com.gerrysatria.githubuser.core.data.source.remote.RemoteDataSource
import com.gerrysatria.githubuser.core.data.source.remote.network.ApiService
import com.gerrysatria.githubuser.core.domain.repository.IUserRepository
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<UserDatabase>().userDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java, "User.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor{ chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", BuildConfig.TOKEN_API)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IUserRepository> {
        UserRepository(get(), get())
    }
}