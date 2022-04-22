package com.yhe64.treasurehunt.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.concurrent.Executors

class PointRespository private constructor(context: Context){
    private val database: PointDatabase = Room.databaseBuilder(
        context.applicationContext,
        PointDatabase::class.java,
        "point_database"
    ).build()
    private val pointDao = database.pointDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllPoints(): LiveData<List<Point>> = pointDao.getAllPoints()

    fun insert(point: Point){
        executor.execute{
            pointDao.insert(point)
        }
    }

    fun deletePoint(point: Point){
        executor.execute{
            pointDao.deletePoint(point)
        }
    }

    fun updatePoint(point: Point){
        executor.execute{
            pointDao.updatePoint(point)
        }
    }

    companion object{
        private var INSTANCE: PointRespository? = null
        fun initialize(context: Context){
            if (INSTANCE == null)
                INSTANCE = PointRespository(context)
        }
        fun get(): PointRespository{
            return INSTANCE?:throw IllegalAccessException("PointRepository must be initialized!")
        }
    }
}