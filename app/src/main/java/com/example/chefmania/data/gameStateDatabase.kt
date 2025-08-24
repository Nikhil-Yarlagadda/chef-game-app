package com.example.chefmania.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GameStateEntity::class], version = 1, exportSchema = false)
abstract class gameStateDatabase : RoomDatabase() {
    abstract fun gameStateDao(): GameStateDao
}