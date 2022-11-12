package com.adrianusid.githubapp

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrianusid.githubapp.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback

class GitViewModel() : ViewModel() {
    private val _response = MutableLiveData<List<ItemsItem>>()
    val response = _response

    private val _detailUser = MutableLiveData<DetailUser>()
    val detailUser = _detailUser

    private val _follower = MutableLiveData<List<Follow>>()
    val follower = _follower

    private val _following = MutableLiveData<List<Follow>>()
    val following = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading


    fun searchUser(name: String, context: Context) {
        _isLoading.value = true
        val search = ApiConfig.getApiService().getUser(name)
        search.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {

                if (response.isSuccessful) {
                    _isLoading.value = false
                    if (response.body() != null) {
                        _response.value = response.body()?.items
                    } else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, "Search Gagal", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getDetailUser(img: String?, context: Context) {

        val getDetail = img?.let { ApiConfig.getApiService().getDetailUser(it) }
        getDetail?.enqueue(object : Callback<DetailUser> {
            override fun onResponse(
                call: Call<DetailUser>,
                response: retrofit2.Response<DetailUser>
            ) {

                if (response.isSuccessful) {

                    if (response.body() != null) {
                        val detailUser = DetailUser(
                            response.body()?.avatarUrl,
                            response.body()?.login,
                            response.body()?.name,
                            response.body()?.company,
                            response.body()?.location,
                            response.body()?.repos
                        )

                        _detailUser.value = detailUser

                    } else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()

                    }
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {

                Toast.makeText(context, "Get Detail Gagal", Toast.LENGTH_SHORT).show()

            }

        })
    }

    fun getFollower(name: String, context: Context) {
        _isLoading.value = true
        val getFollower = ApiConfig.getApiService().getFollower(name)
        getFollower.enqueue(object : Callback<List<Follow>> {
            override fun onResponse(
                call: Call<List<Follow>>,
                response: retrofit2.Response<List<Follow>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false

                    if (response.body() != null) {
                        follower.postValue((response.body()))
                    } else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()

                    }
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Follow>>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, "Get Follower Gagal", Toast.LENGTH_SHORT).show()

            }

        })

    }

    fun getFollowing(name: String, context: Context) {
        _isLoading.value = true
        val getFollowing = ApiConfig.getApiService().getFollowing(name)
        getFollowing.enqueue(object : Callback<List<Follow>> {
            override fun onResponse(
                call: Call<List<Follow>>,
                response: retrofit2.Response<List<Follow>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false


                    if (response.body() != null) {
                        following.postValue((response.body()))
                    } else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()

                    }
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Follow>>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, "Get Following Gagal", Toast.LENGTH_SHORT).show()

            }

        })

    }


}
