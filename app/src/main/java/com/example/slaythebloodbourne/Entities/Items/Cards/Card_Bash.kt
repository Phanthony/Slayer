package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

class Card_Bash(val player: Character):Card {
    override val attack = 3
    override val block  = 3
    override val energyCost = 1
    override val special = false

    override fun ability(player: Character, enemy: Enemy) {

    }

    override val name = "Shield Bash"
    override val description: String
        get() = "Block ${player.playerBonusBlock+block} damage\nDeal ${player.playerBonusAttack+attack} damage"

}