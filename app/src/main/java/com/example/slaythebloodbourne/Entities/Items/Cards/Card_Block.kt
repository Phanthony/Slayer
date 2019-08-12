package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

class Card_Block(override var player: Character) : Card {
    override fun getDesc(): String {
        return "Add $block block"
    }

    override val energyCost = 1
    override val attack: Int? = null
    override val block = 5
    override val name = "Block"
    override var description = getDesc()
    override val special = false

    override fun ability(player: Character, enemy: Enemy) {

    }

}