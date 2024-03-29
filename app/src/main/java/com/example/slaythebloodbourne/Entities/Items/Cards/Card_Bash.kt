package com.example.slaythebloodbourne.Entities.Items.Cards

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

class Card_Bash(override var player: Character):Card {
    override fun getDesc(): String {
        return "Add $block block and Deal ${player.playerBonusAttack+attack+player.tempAttack} damage"
    }

    override val attack = 3
    override val block  = 3
    override val energyCost = 1
    override val special = false

    override fun ability(player: Character, enemy: Enemy) {

    }

    override val name = "Shield Bash"
    override var description = getDesc()
}