package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

class Card_Bite(override var player: Character): Card {
    override fun getDesc(): String {
        return "Deal ${attack+player.playerBonusAttack+player.tempAttack} damage\nSteal 2 health"
    }

    override val energyCost = 1
    override val attack = 3
    override val block = 0
    override val name = "Bite"
    override var description = getDesc()
    override val special = true

    override fun ability(player: Character, enemy: Enemy) {
        enemy.takeDamage(2)
        player.getHealth(2)
    }

}