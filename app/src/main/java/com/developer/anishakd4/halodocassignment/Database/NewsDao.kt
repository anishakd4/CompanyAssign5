package com.developer.anishakd4.halodocassignment.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.developer.anishakd4.halodocassignment.Model.HitsModel

@Dao
interface NewsDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(night: HitsModel)

    @Query("SELECT * FROM News")
    fun getAllNews(): LiveData<List<HitsModel>>

}