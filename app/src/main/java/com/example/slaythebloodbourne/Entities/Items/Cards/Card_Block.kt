package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

class Card_Block(private val player: Character) : Card {
    override val energyCost = 1
    override val attack = 0
    override val block = 5
    override val name = "Block"
    override val description
        get() = updateDescription()
    override val special = false

    override fun ability(player: Character, enemy: Enemy) {

    }

    private fun updateDescription(): String {
        return "Block ${player.playerBonusBlock + block} damage"
    }
}