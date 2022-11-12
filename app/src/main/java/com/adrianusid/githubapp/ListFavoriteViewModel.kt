package com.adrianusid.githubapp

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianusid.githubapp.database.Favorite
import com.adrianusid.githubapp.repository.FavoriteRepository

class ListFavoriteViewModel(application: Application): ViewModel(){
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()


}