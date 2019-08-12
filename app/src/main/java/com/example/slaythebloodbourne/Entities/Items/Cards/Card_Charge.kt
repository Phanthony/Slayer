package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

class Card_Charge(override var player: Character): Card {
    override fun getDesc(): String {
        return "Deal ${player.playerBonusAttack + attack+player.tempAttack} damage\nLose 2 block"
    }

    override val attack = 12
    override val block = 0
    override val energyCost = 1
    override val special= true

    override fun ability(player: Character, enemy: Enemy) {
        player.tempBlock -= 2
    }

    override val name = "Charge"
    override var description = getDesc()

}