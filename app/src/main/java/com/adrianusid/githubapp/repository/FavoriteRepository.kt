package com.adrianusid.githubapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.adrianusid.githubapp.database.Favorite
import com.adrianusid.githubapp.database.FavoriteDao
import com.adrianusid.githubapp.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavDao = db.favDao()
    }

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavDao.getAllFavorite()

    suspend fun selectFavorite(login: String) = mFavDao.selectFavorite(login)

     fun insert(fav: Favorite) {
        executorService.execute { mFavDao.insert(fav) }
    }

    fun delete(fav: Favorite) {
        executorService.execute { mFavDao.delete(fav) }
    }

}