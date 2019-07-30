package com.example.slaythebloodbourne.Modules

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Items.Cards.Card


@Entity(tableName = "pathway_table")
data class PathWayEntity(
    @PrimaryKey var id: Int,
    var roomSelections: ArrayList<Int>,
    var player: Character, var floorCount: Int,
    var playerHand: ArrayList<Card>,
    var playerDiscard: ArrayList<Card>,
    var playerDeck: ArrayList<Card>
) {
}