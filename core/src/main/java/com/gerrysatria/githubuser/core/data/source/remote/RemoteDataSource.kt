package com.gerrysatria.githubuser.core.data.source.remote

import android.util.Log
import com.gerrysatria.githubuser.core.data.source.remote.network.ApiResponse
import com.gerrysatria.githubuser.core.data.source.remote.network.ApiService
import com.gerrysatria.githubuser.core.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getUsers(q: String) : Flow<ApiResponse<List<UserResponse>>> =
        flow {
            try {
                val response = apiService.findUsers(q)
                val dataArray = response.users
                if (dataArray.isEmpty()){
                    emit(ApiResponse.Error("null"))
                } else {
                    emit(ApiResponse.Success(response.users))
                }
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailUser(user: String) : Flow<ApiResponse<UserResponse>> =
        flow {
            try {
                val response = apiService.getDetailUser(user)
                emit(ApiResponse.Success(response))
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getFollowersUser(user: String) : Flow<ApiResponse<List<UserResponse>>> =
        flow {
            try {
                val response = apiService.getListFollowersUser(user)
                emit(ApiResponse.Success(response))
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getFollowingsUser(user: String) : Flow<ApiResponse<List<UserResponse>>> =
        flow {
            try {
                val response = apiService.getListFollowingsUser(user)
                emit(ApiResponse.Success(response))
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
}