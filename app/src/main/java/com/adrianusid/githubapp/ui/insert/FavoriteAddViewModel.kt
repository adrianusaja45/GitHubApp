package com.adrianusid.githubapp.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.adrianusid.githubapp.database.Favorite
import com.adrianusid.githubapp.repository.FavoriteRepository

class FavoriteAddViewModel (application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(fav: Favorite){
        mFavoriteRepository.insert(fav)
    }

    fun delete(fav: Favorite){
        mFavoriteRepository.delete(fav)
    }

    suspend fun selectFavorite(login: String) = mFavoriteRepository.selectFavorite(login)


}