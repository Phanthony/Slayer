package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

class Card_Bite(val player: Character): Card {
    override val energyCost = 1
    override val attack = 3
    override val block = 0
    override val name = "Bite"
    override val description: String
        get() = updateDescription()
    override val special = true

    override fun ability(player: Character, enemy: Enemy) {
        enemy.takeDamage(2)
        player.getHealth(2)
    }

    private fun updateDescription(): String{
        return "Deal ${attack+player.playerBonusAttack} damage\nSteal 2 health"
    }
}