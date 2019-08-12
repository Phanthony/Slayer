package com.example.slaythebloodbourne.Modules

import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy

const val BATTLE_ONGOING = 0
const val BATTLE_LOST = 1
const val BATTLE_WON = 2

class TurnEngine(val player: Character, private val enemy: Enemy) {
    var battleState = BATTLE_ONGOING

    fun startTurn() {
        val enemyMove = enemy.chooseMove()
        if (battleState == BATTLE_ONGOING) {
            enemy.enemyBlock = enemyMove.block
            enemy.enemyAttack = enemyMove.attack
            playerDrawFromHand()
        }
        player.playerCurrentEnergy = player.energy
    }

    private fun playerDrawFromHand() {
        if (battleState == BATTLE_ONGOING) {
            while (player.playerHand.size < player.maxCards) {
                if (player.playerDeck.isEmpty()) {
                    resetPlayerDeck(player.playerDiscard, player.playerDeck)
                }
                player.playerHand.add(player.playerDeck.removeAt(0))
            }
        }
    }

    fun endTurn(): Int? {
        var enemyDamageDone: Int? = null
        if (battleState == BATTLE_ONGOING) {
            enemyDamageDone = when (enemy.enemyAttack + enemy.tempDamage) {
                0 -> null
                else -> if (enemy.enemyAttack + enemy.tempDamage - player.playerBlock - player.tempBlock - player.playerBonusBlock > 0)
                    enemy.enemyAttack + enemy.tempDamage - player.playerBlock - player.tempBlock - player.playerBonusBlock else 0
            }
            if (enemyDamageDone != null) {
                player.takeDamage(enemyDamageDone)
            }
            if (player.playerCurrentHealth <= 0) {
                battleState = BATTLE_LOST; return null
            }
        }
        resetTurn()
        return enemyDamageDone
    }


    private fun resetPlayerDeck(discardPile: ArrayList<Card>, deckPile: ArrayList<Card>) {

        discardPile.shuffle()
        deckPile.addAll(discardPile)
        discardPile.clear()
    }

    fun enemyEnergyIncrease() {
        if (enemy.specialAbility) {
            if (enemy.enemyCurrentEnergy < enemy.energy) {
                enemy.enemyCurrentEnergy++
            } else {
                enemy.enemyCurrentEnergy = 0
                enemy.ability(player)
            }
        }
    }

    fun checkPlayerEnoughEnergy(card: Card): Boolean {
        return (card.energyCost <= player.playerCurrentEnergy)
    }

    fun playCard(card: Card): Int? {
        if (battleState == BATTLE_ONGOING) {
            player.playerCurrentEnergy -= card.energyCost
            if (card.special) card.ability(player, enemy)
            if (card.block != null) {
                player.playerBlock += card.block!!
            }
            val playerTotalAttack = player.playerBonusAttack + player.tempAttack
            val enemyTotalBlock = enemy.enemyBlock + enemy.tempBlock
            val playerDamageDone = when (card.attack) {
                null -> null
                0 -> 0
                else -> {
                    if ((card.attack!! + playerTotalAttack) - enemyTotalBlock > 0) {
                        val r = (card.attack!! + playerTotalAttack) - enemyTotalBlock
                        enemy.enemyBlock = (enemy.tempBlock * -1)
                        r
                    } else {
                        enemy.enemyBlock -= (card.attack!! + player.playerBonusAttack + player.tempAttack)
                        0
                    }
                }
            }
            if (playerDamageDone != null) {
                enemy.takeDamage(playerDamageDone)
            }
            player.playerDiscard.add(card)
            val pos = player.playerHand.indexOf(card)
            player.playerHand.removeAt(pos)
            if (enemy.enemyCurrentHealth <= 0) {
                battleState = BATTLE_WON
            }
            return playerDamageDone
        }
        return null
    }

    private fun resetTurn() {
        enemy.enemyAttack = 0
        enemy.enemyBlock = 0
        enemy.tempDamage = 0
        enemy.tempBlock = 0
        player.playerBlock = 0
        player.playerDiscard.addAll(player.playerHand)
        player.playerHand.clear()
        player.tempBlock = 0
        player.tempAttack = 0

    }
}