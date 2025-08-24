package com.example.chefmania.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Gamestate")
data class GameStateEntity(
    @PrimaryKey
    val id: Int = 0,
    val ongoing: Int = 0,
    val board: String,
    val turn: Int,
    val moves: String,
    val ai: Int
)
