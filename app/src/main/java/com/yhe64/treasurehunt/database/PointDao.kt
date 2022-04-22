package com.yhe64.treasurehunt.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PointDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(point: Point)

    @Query("DELETE FROM point_table")
    fun deleteAll()

    @Delete
    fun deletePoint(point: Point)

    @Update
    fun updatePoint(point: Point)

    @Query("SELECT * FROM point_table LIMIT 1")
    fun getAnyPoint(): Array<Point>

    @Query("SELECT * FROM  point_table ORDER BY id DESC")
    fun getAllPoints(): LiveData<List<Point>>
}