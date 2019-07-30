package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

class Card_AttackBreak(override var player: Character): Card {
    override val attack = 0
    override val block  = 0
    override val energyCost = 1
    override val special = true

    override fun ability(player: Character, enemy: Enemy) {
        enemy.tempDamage -= 3
    }

    override val name = "Attack Break"
    override val description: String
        get() = "Enemy loses 3 damage"
}