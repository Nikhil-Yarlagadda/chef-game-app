package com.example.chefmania.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class savedGame(
    @PrimaryKey
    val id: Int = 0,
    val movesMade: List<Move>
)
