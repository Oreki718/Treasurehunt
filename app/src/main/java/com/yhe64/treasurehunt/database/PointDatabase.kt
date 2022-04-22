package com.yhe64.treasurehunt.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Point::class], version = 1, exportSchema = false)
abstract class PointDatabase : RoomDatabase(){
    abstract fun pointDao(): PointDao
}