package com.yhe64.treasurehunt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.yhe64.treasurehunt.database.PointRespository
import com.yhe64.treasurehunt.database.Point

class PointViewModel(app:Application) : AndroidViewModel(app) {

    init{
        PointRespository.initialize(app)
    }

    private val pointRepository = PointRespository.get()
    val points = pointRepository.getAllPoints()

    fun insert(point: Point){
        pointRepository.insert(point)
    }

    fun delete(point: Point){
        pointRepository.deletePoint(point)
    }

    fun update(point: Point){
        pointRepository.updatePoint(point)
    }

}