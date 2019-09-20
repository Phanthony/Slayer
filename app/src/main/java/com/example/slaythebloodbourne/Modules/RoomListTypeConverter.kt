package com.example.slaythebloodbourne.Modules

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomListTypeConverter {

    val gson = Gson()

    @TypeConverter
    fun roomListToString(roomList: ArrayList<Int>): String = gson.toJson(roomList)

    @TypeConverter
    fun stringToRoomList(roomList: String): ArrayList<Int>{
        val arrayListType = object : TypeToken<ArrayList<Int>>(){}.type
        return gson.fromJson(roomList,arrayListType)
    }
}