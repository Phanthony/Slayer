package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy
import com.example.slaythebloodbourne.Entities.Items.Item

interface Card: Item {
    val attack: Int
    val block: Int
    val energyCost: Int
    val special: Boolean

    fun ability(player: Character, enemy: Enemy)
}