package com.adrianusid.githubapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(fav: Favorite)

    @Delete
    fun delete(fav: Favorite)

    @Query("SELECT* from favorite ORDER BY login ASC ")
    fun getAllFavorite(): LiveData<List<Favorite>>

    @Query("SELECT  COUNT(*) from favorite WHERE Login = :login")
    suspend fun selectFavorite(login: String): Int
}