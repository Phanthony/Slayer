package com.example.slaythebloodbourne.Modules

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy
import com.example.slaythebloodbourne.Entities.Enemies.Move
import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.example.slaythebloodbourne.Entities.Items.Item


@Entity(tableName = "pathway_table")
data class PathWayEntity(
    @PrimaryKey var id: Int,
    var roomSelections: ArrayList<Int>,
    var player: Character, var floorCount: Int,
    var playerHand: ArrayList<Card>,
    var playerDiscard: ArrayList<Card>,
    var playerDeck: ArrayList<Card>,
    var currentPosition: Int
)

//current position codes
// enemy or boss -- 0
// store -- 1
// chest -- 2
// shrine -- 3
// reward -- 4
// pathway -- 5

@Entity(tableName = "enemy_table")
data class EnemyTable(
    @PrimaryKey var id: Int,
    var enemy: Enemy
)

@Entity(tableName = "store_table")
data class StoreTable(
    @PrimaryKey var id: Int,
    var itemList: ArrayList<Item>,
    var goldList: ArrayList<Int>
)

@Entity(tableName = "chest_table")
data class ChestTable(
    @PrimaryKey var id: Int,
    var goldReward: Int,
    var cardReward: Card?
)

@Entity(tableName = "shrine_table")
data class ShrineTable(
    @PrimaryKey var id: Int,
    var reward: Move
)






