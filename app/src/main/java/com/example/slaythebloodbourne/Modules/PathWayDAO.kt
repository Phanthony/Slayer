package com.example.slaythebloodbourne.Modules

import androidx.fragment.app.Fragment
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.slaythebloodbourne.Activities.PathwayFragment

@Dao
interface PathWayDAO{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pathway: PathWayEntity)

    @Query("SELECT * FROM pathway_table")
    fun getPathway(): PathWayEntity?

    @Query("DELETE FROM pathway_table")
    fun resetGame()
}