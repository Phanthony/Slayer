package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

class Card_Strike(private val player: Character): Card {
    override val description: String
        get() = updateDescription()
    override val energyCost = 1
    override val attack = 5
    override val block = 0
    override val name = "Strike"
    override val special = false

    override fun ability(player: Character, enemy: Enemy) {
    }

    private fun updateDescription():String{
        return "Deal ${attack+player.playerBonusAttack} damage"
    }
}