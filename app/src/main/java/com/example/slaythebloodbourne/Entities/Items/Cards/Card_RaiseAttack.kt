package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

class Card_RaiseAttack(override var player: Character):Card {
    override val attack = 0
    override val block = 0
    override val energyCost = 1
    override val special = true

    override fun ability(player: Character, enemy: Enemy) {
        player.tempAttack += 3
    }

    override val name = "Raise Attack"
    override val description = "Raise attack by 3"
}