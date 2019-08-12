package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

class Card_AttackBreak(override var player: Character): Card {
    override fun getDesc(): String {
        return "Enemy loses 3 attack"
    }

    override val attack = 0
    override val block  = 0
    override val energyCost = 1
    override val special = true

    override fun ability(player: Character, enemy: Enemy) {
        enemy.tempDamage -= 3
    }

    override val name = "Attack Break"
    override var description = getDesc()
}