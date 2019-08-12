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
    fun insertPathway(pathway: PathWayEntity)

    @Query("SELECT * FROM pathway_table")
    fun getPathway(): PathWayEntity?

    @Query("DELETE FROM pathway_table")
    fun resetGame()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEnemy(enemy: EnemyTable)

    @Query("SELECT * FROM enemy_table")
    fun getEnemy(): EnemyTable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShop(store: StoreTable)

    @Query("SELECT * FROM store_table")
    fun getShop(): StoreTable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChest(chest: ChestTable)

    @Query("SELECT * FROM chest_table")
    fun getChest(): ChestTable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShrine(shrine: ShrineTable)

    @Query("SELECT * FROM shrine_table")
    fun getShrine(): ShrineTable
}