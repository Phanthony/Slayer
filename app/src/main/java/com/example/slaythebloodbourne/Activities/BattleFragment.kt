package com.example.slaythebloodbourne.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slaythebloodbourne.*
import com.example.slaythebloodbourne.Entities.Cards.Card
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy
import com.example.slaythebloodbourne.Entities.Enemies.Enemy_Zombie
import com.example.slaythebloodbourne.Modules.RecyclerViewAdapter

class  BattleFragment(private val player: Character): Fragment() {


    lateinit var enemyAttackText: TextView
    lateinit var enemyBlockText: TextView
    lateinit var enemyHealthBar: ProgressBar

    lateinit var playerHealthBar : ProgressBar
    lateinit var playerHealthText : TextView
    lateinit var playerEnergyText : TextView

    lateinit var endTurnButton : Button

    lateinit var turnEngine: TurnEngine

    lateinit var enemy : Enemy


    private val adapter = RecyclerViewAdapter(arrayListOf(), arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        enemy = Enemy_Zombie()
        when((0..1).random()){
            1 -> {enemy.enemyCurrentHealth = 10}
            0 -> {enemy.enemyCurrentHealth = 15}
        }
        turnEngine = TurnEngine(player,enemy)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.battle_fragment_layout,container,false)

        //Get UI pieces that update
        enemyAttackText = view.findViewById(R.id.enemyAttackNumber)
        enemyBlockText = view.findViewById(R.id.enemyBlockNumber)
        updateEnemyMoves()
        enemyHealthBar = view.findViewById(R.id.enemyHealthBar)
        enemyHealthBar.max = enemy.health
        updateEnemyHealthBar()

        playerHealthBar = view.findViewById(R.id.playerHealthBar)
        playerHealthBar.max = player.health
        updatePlayerHealthBar()

        playerHealthText = view.findViewById(R.id.healthNumberValue)
        updatePlayerHealthText()
        playerEnergyText = view.findViewById(R.id.energyNumberValue)
        updatePlayerEnergyText()

        endTurnButton = view.findViewById(R.id.endTurnButton)

        endTurnButton.setOnClickListener {
            endTurn()
        }

        val cardList = view.findViewById<RecyclerView>(R.id.cardRecycleView)
        cardList.layoutManager = LinearLayoutManager(this.context,RecyclerView.HORIZONTAL,false)
        cardList.adapter = adapter

        cardButtonBuilder(player.playerHand)

        return view

    }

    private fun cardButtonBuilder(arrayList: ArrayList<Card>){
        adapter.clearCards()
        val cardList = arrayListOf<Card>()
        val listenerList = arrayListOf<OnClickListener>()
        for (card in arrayList){
            val clickListener = OnClickListener{
                if(useCard(card)){
                    val index = adapter.cardList.indexOf(card)
                    adapter.deleteCard(index)
                }
            }
            cardList.add(card)
            listenerList.add(clickListener)
        }
        adapter.addCards(cardList,listenerList)
    }

    private fun useCard(card: Card): Boolean{
        val playable = turnEngine.checkPlayerEnoughEnergy(card)
        if(playable){
            turnEngine.playCard(card)
            updateEnemyHealthBar()
            updatePlayerEnergyText()
            checkBattleState()
        }
        return playable
    }


    private fun endTurn(){
        turnEngine.endTurn()
        updatePlayerHealthBar()
        updatePlayerHealthText()
        updatePlayerEnergyText()
        updateEnemyMoves()
        updateEnemyHealthBar()
        checkBattleState()
        cardButtonBuilder(player.playerHand)
    }

    private fun checkBattleState(){
        when(turnEngine.battleState){
            BATTLE_LOST -> {
                leaveGame()
            }
            BATTLE_WON -> {
                leaveBattleFragment()
            }
        }
    }

    private fun updatePlayerHealthBar(){
        playerHealthBar.progress = player.health - player.playerCurrentHealth
    }

    private fun updatePlayerEnergyText(){
        playerEnergyText.text = "${player.playerCurrentEnergy}"
    }

    private fun updatePlayerHealthText(){
        playerHealthText.text = "${player.playerCurrentHealth}/${player.health}"
    }

    private fun updateEnemyHealthBar(){
        enemyHealthBar.progress = enemy.health - enemy.enemyCurrentHealth
    }

    private fun updateEnemyMoves(){
        enemyBlockText.text = "${enemy.enemyBlock}"
        enemyAttackText.text = "${enemy.enemyAttack}"
    }

    private fun leaveBattleFragment(){
        val main = (activity as FullscreenActivity)
        main.goBackOneFragment()
    }

    private fun leaveGame(){
        val main = (activity as FullscreenActivity)
        main.createNewGame()
    }
}