package com.yhe64.treasurehunt.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "point_table")
data class Point (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,
    @ColumnInfo(name = "name")
    var name : String = "",
    @ColumnInfo(name = "x_position")
    var x_position : Int = 0,
    @ColumnInfo(name = "y_position")
    var y_position : Int = 0,
    @ColumnInfo(name = "x_distance")
    var x_distance : Int = 0,
    @ColumnInfo(name = "y_distance")
    var y_distance : Int = 0,
    @ColumnInfo(name = "score")
    var score : Int = 0
)