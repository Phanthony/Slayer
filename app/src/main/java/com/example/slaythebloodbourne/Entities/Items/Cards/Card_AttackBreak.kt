package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

class Card_AttackBreak(val player: Character): Card {
    override val attack = 0
    override val block  = 0
    override val energyCost = 1
    override val special = true

    override fun ability(player: Character, enemy: Enemy) {
        val calc = enemy.bonusDamage - 3
        enemy.bonusDamage = when{
            calc > 0 -> calc
            else -> 0
        }
    }

    override val name = "Attack Break"
    override val description: String
        get() = "Enemy loses 3 damage next turn"
}