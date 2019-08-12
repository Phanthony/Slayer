package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

class Card_RaiseAttack(override var player: Character) : Card {
    override fun getDesc(): String {
        return "Raise attack by 3"
    }

    override val attack: Int? = null
    override val block: Int? = null
    override val energyCost = 1
    override val special = true

    override fun ability(player: Character, enemy: Enemy) {
        player.tempAttack += 3
    }

    override val name = "Raise Attack"
    override var description = getDesc()
}