package com.gerrysatria.githubuser.core.di

import androidx.room.Room
import com.gerrysatria.githubuser.core.BuildConfig
import com.gerrysatria.githubuser.core.data.UserRepository
import com.gerrysatria.githubuser.core.data.source.local.LocalDataSource
import com.gerrysatria.githubuser.core.data.source.local.room.UserDatabase
import com.gerrysatria.githubuser.core.data.source.remote.RemoteDataSource
import com.gerrysatria.githubuser.core.data.source.remote.network.ApiService
import com.gerrysatria.githubuser.core.domain.repository.IUserRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<UserDatabase>().userDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("githubuser".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java, "User.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "api.github.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/lmo8/KPXoMsxI+J9hY+ibNm2r0IYChmOsF9BxD74PVc=")
            .add(hostname, "sha256/6YBE8kK4d5J1qu1wEjyoKqzEIvyRY5HyM/NB2wKdcZo=")
            .add(hostname, "sha256/ICGRfpgmOUXIWcQ/HXPLQTkFPEFPoDyjvH7ohhQpjzs=")
            .build()
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
            .certificatePinner(certificatePinner)
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