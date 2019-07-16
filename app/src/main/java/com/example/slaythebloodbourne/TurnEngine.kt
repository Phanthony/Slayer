package com.example.slaythebloodbourne

import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy
import com.example.slaythebloodbourne.Entities.Enemies.Move

const val BATTLE_ONGOING = 0
const val BATTLE_LOST = 1
const val BATTLE_WON = 2

class TurnEngine(val player: Character, private val enemy: Enemy) {

    var battleState = BATTLE_ONGOING
    var enemyMove = startTurn()

    private fun startTurn(): Move {
        enemyEnergyIncrease()
        val enemyMove = enemy.chooseMove()
        if (battleState == BATTLE_ONGOING) {
            enemy.enemyBlock = enemyMove.block
            enemy.enemyAttack = enemyMove.attack
            playerDrawFromHand()
        }
        player.playerCurrentEnergy = player.energy
        return enemyMove
    }

    fun playerDrawFromHand() {
        if (battleState == BATTLE_ONGOING) {
            while (player.playerHand.size < player.maxCards) {
                if (player.playerDeck.isEmpty()) {
                    resetPlayerDeck(player.playerDiscard, player.playerDeck)
                }
                player.playerHand.add(player.playerDeck.removeAt(0))
            }
        }
    }

    fun endTurn() {
        if (battleState == BATTLE_ONGOING) {
            val enemyDamageDone =
                if (enemy.enemyAttack - player.playerBlock > 0) enemy.enemyAttack - player.playerBlock else 0
            player.takeDamage(enemyDamageDone)
            if (player.playerCurrentHealth <= 0) {
                battleState = BATTLE_LOST; return
            }
        }
        resetTurn()
    }


    private fun resetPlayerDeck(discardPile: ArrayList<Card>, deckPile: ArrayList<Card>) {
        discardPile.shuffle()
        deckPile.addAll(discardPile)
        discardPile.clear()
    }

    private fun enemyEnergyIncrease() {
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

    fun playCard(card: Card) {
        if (battleState == BATTLE_ONGOING) {
            player.playerCurrentEnergy -= card.energyCost
            if (card.special) card.ability(player, enemy)
            player.playerBlock += card.block
            val playerDamageDone =
                when (card.attack) {
                    0 -> 0
                    else -> {
                        if (card.attack + player.playerBonusAttack - enemy.enemyBlock > 0) card.attack + player.playerBonusAttack - enemy.enemyBlock else 0
                    }
                }
            enemy.takeDamage(playerDamageDone)
            player.playerDiscard.add(card)
            val pos = player.playerHand.indexOf(card)
            player.playerHand.removeAt(pos)
            if (enemy.enemyCurrentHealth <= 0) {
                battleState = BATTLE_WON
            }
        }
    }

    private fun resetTurn() {
        player.playerBlock = 0
        player.playerDiscard.addAll(player.playerHand)
        player.playerHand.clear()
        enemyMove = startTurn()
    }
}