package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

class Card_Strike(override var player: Character): Card {
    override val description: String
        get() = "Deal ${attack+player.playerBonusAttack+player.tempAttack} damage"
    override val energyCost = 1
    override val attack = 5
    override val block = 0
    override val name = "Strike"
    override val special = false

    override fun ability(player: Character, enemy: Enemy) {
    }
}