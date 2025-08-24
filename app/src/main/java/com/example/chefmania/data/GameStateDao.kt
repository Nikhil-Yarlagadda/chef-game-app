package com.example.chefmania.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameStateDao {

    @Query("SELECT COUNT(*) FROM gamestate")
    suspend fun count(): Int

    @Query("SELECT * FROM gamestate WHERE id = 0")
    suspend fun get(): GameStateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(state: GameStateEntity)
}