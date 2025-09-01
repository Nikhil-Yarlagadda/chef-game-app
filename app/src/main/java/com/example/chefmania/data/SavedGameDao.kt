package com.example.chefmania.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedGameDao {
    @Query("SELECT COUNT(*) FROM games")
    suspend fun count(): Int

    @Query("SELECT * FROM games WHERE id = :id")
    suspend fun get(id: Int): Flow<savedGame>

    @Query("SELECT * from games ORDER BY id")
    fun getAllGames(): Flow<List<savedGame>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(savedGame: savedGame)
}