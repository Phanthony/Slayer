package com.example.slaythebloodbourne.Modules

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.slaythebloodbourne.Activities.PathwayFragment

@Database(entities = [PathWayEntity::class], version = 1)
@TypeConverters(RoomListTypeConverter::class,CharacterTypeConverter::class)
abstract class PathWayDatabase : RoomDatabase() {

    abstract fun pathwayDAO(): PathWayDAO

    companion object {
        private var instance: PathWayDatabase? = null

        @Synchronized
        fun getInstance(context: Context): PathWayDatabase? {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        PathWayDatabase::class.java,
                        "pathway_database"
                    )
                        .build()
                }
                return instance
            }
        }
    }

}