package com.example.slaythebloodbourne.Activities

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slaythebloodbourne.Modules.BATTLE_LOST
import com.example.slaythebloodbourne.Modules.BATTLE_WON
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy
import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.example.slaythebloodbourne.Modules.RecyclerViewCardsAdapter
import com.example.slaythebloodbourne.R
import com.example.slaythebloodbourne.Modules.TurnEngine
import kotlinx.coroutines.*

class BattleFragment(private val player: Character, val floor: Int, val enemy: Enemy, val continuation: Boolean = false) : Fragment() {

    lateinit var enemyHealthBar: ProgressBar
    lateinit var enemyHealthText: TextView

    lateinit var playerHealthBar: ProgressBar
    lateinit var playerHealthText: TextView
    lateinit var playerEnergyText: TextView
    lateinit var playerBonusAttack: TextView
    lateinit var playerBonusBlock: TextView

    lateinit var endTurnButton: Button

    lateinit var turnEngine: TurnEngine

    lateinit var enemyAction: LinearLayout

    private val adapter = RecyclerViewCardsAdapter(arrayListOf(), arrayListOf())

    var goldDropped: Int = 0

    lateinit var playerLeft: Guideline
    lateinit var playerRight: Guideline
    lateinit var playerTop: Guideline
    lateinit var playerBottom: Guideline

    lateinit var enemyLeft: Guideline
    lateinit var enemyRight: Guideline
    lateinit var enemyTop: Guideline
    lateinit var enemyBottom: Guideline

    lateinit var enemyDameDoneLayout: LinearLayout
    lateinit var playerDameDoneLayout: LinearLayout

    lateinit var mainLayout: ConstraintLayout
    lateinit var inflater: LayoutInflater

    override fun onCreate(savedInstanceState: Bundle?) {
        val main = (activity as FullscreenActivity)
        goldDropped = main.randomGold(floor)
        turnEngine = TurnEngine(player, enemy)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        this.inflater = inflater
        val view = inflater.inflate(R.layout.battle_fragment_layout, container, false)

        view.findViewById<AppCompatImageView>(R.id.enemyImage).setImageResource(enemy.image)

        enemyAction = view.findViewById(R.id.enemy_action_layout)
        enemyDameDoneLayout = view.findViewById(R.id.enemy_attack_number_display_layout)
        playerDameDoneLayout = view.findViewById(R.id.player_attack_number_display_layout)

        //Get UI pieces that update
        if (enemy.enemyBlock != 0) {
            val layoutBuilder = LayoutInflater.from(context)
            val blockLayout = layoutBuilder.inflate(R.layout.enemy_defense_layout, enemyAction)
            blockLayout.findViewById<TextView>(R.id.enemy_defense_text_view).text = "${enemy.enemyBlock}"
        }

        if (enemy.enemyAttack != 0) {
            val layoutBuilder = LayoutInflater.from(context)
            val attackLayout = layoutBuilder.inflate(R.layout.enemy_attack_layout, enemyAction)
            attackLayout.findViewById<TextView>(R.id.enemy_attack_text_view).text = "${enemy.enemyAttack}"
        }

        enemyHealthText = view.findViewById(R.id.enemy_health_text)
        enemyHealthBar = view.findViewById(R.id.enemyHealthBar)
        enemyHealthBar.max = enemy.health
        updateEnemyHealth()

        playerHealthBar = view.findViewById(R.id.playerHealthBar)
        playerHealthText = view.findViewById(R.id.player_health_text)
        playerHealthBar.max = player.health
        updatePlayerHealth()

        playerEnergyText = view.findViewById(R.id.energy_text_view)
        updatePlayerEnergyText()

        playerBonusAttack = view.findViewById(R.id.attack_text_view)
        updatePlayerAttack()
        playerBonusBlock = view.findViewById(R.id.defense_text_view)
        updatePlayerBlock()

        playerLeft = view.findViewById(R.id.player_left_guideline)
        playerRight = view.findViewById(R.id.player_right_guideline)
        playerTop = view.findViewById(R.id.player_top_guideline)
        playerBottom = view.findViewById(R.id.player_bottom_guideline)

        enemyLeft = view.findViewById(R.id.enemy_left_guideline)
        enemyRight = view.findViewById(R.id.enemy_right_guideline)
        enemyTop = view.findViewById(R.id.enemy_top_guideline)
        enemyBottom = view.findViewById(R.id.enemy_bottom_guideline)

        endTurnButton = view.findViewById(R.id.finish_turn_button)

        endTurnButton.setOnClickListener {
            endTurn()
        }

        val cardList = view.findViewById<RecyclerView>(R.id.cardRecycleView)
        cardList.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        cardList.adapter = adapter

        if(enemy.enemyCurrentHealth <= 0){
            turnEngine.battleState = BATTLE_WON
        }

        if(player.playerCurrentHealth<=0){
            turnEngine.battleState = BATTLE_LOST
        }

        if(!continuation) {
            newTurn()
        }
        else{
            if (checkBattleState()) cardButtonBuilder(player.playerHand)
            updateEnemyHealth()
            updateEnemyMoves()
            updatePlayerEnergyText()
            updatePlayerBlock()
            updatePlayerAttack()
        }

        mainLayout = view.findViewById(R.id.mainOuterBattleLayout)

        return view

    }

    private fun cardButtonBuilder(arrayList: ArrayList<Card>) {
        val main = activity as FullscreenActivity
        adapter.clearCards()
        val listenerList = arrayListOf<OnClickListener>()
        for (card in arrayList) {
            val clickListener = OnClickListener {
                if (useCard(card)) {
                    val adapterIndex = adapter.cardList.indexOf(card)
                    adapter.deleteCard(adapterIndex)
                    main.updateEnemyTable(enemy)
                    main.updatePlayerInDatabase(player)
                }
            }
            listenerList.add(clickListener)
        }
        adapter.addCards(arrayList, listenerList)
    }

    //UI Functions
    private fun updatePlayerHealth() {
        playerHealthBar.progress = player.health - player.playerCurrentHealth
        playerHealthText.text = "${player.playerCurrentHealth}"
    }

    private fun updateEnemyHealth() {
        enemyHealthBar.progress = enemy.health - enemy.enemyCurrentHealth
        enemyHealthText.text = "${enemy.enemyCurrentHealth}"
    }

    private fun updatePlayerEnergyText() {
        playerEnergyText.text = "${player.playerCurrentEnergy}"
    }

    private fun updateEnemyMoves(wait: Boolean = true) {
        val context = this.context
        CoroutineScope(Dispatchers.IO).launch {
            if (wait) delay(150)
            withContext(Dispatchers.Main) {
                enemyAction.removeAllViews()
            }
            if (enemy.enemyBlock + enemy.tempBlock != 0) {
                val inflater = LayoutInflater.from(context)
                withContext(Dispatchers.Main) {
                    val blockLayout = inflater.inflate(R.layout.enemy_defense_layout, enemyAction)
                    blockLayout.findViewById<TextView>(R.id.enemy_defense_text_view).text =
                        "${enemy.enemyBlock + enemy.tempBlock}"
                }
            }
            if (enemy.enemyAttack + enemy.tempDamage > 0) {
                val inflater = LayoutInflater.from(context)
                withContext(Dispatchers.Main) {
                    val attackLayout = inflater.inflate(R.layout.enemy_attack_layout, enemyAction)
                    attackLayout.findViewById<TextView>(R.id.enemy_attack_text_view).text =
                        "${enemy.enemyAttack + enemy.tempDamage}"
                }
            }
        }
    }

    private fun updatePlayerAttack() {
        playerBonusAttack.text = "${player.playerBonusAttack + player.tempAttack}"
    }

    private fun updatePlayerBlock() {
        playerBonusBlock.text = "${player.playerBonusBlock + player.tempBlock + player.playerBlock}"
    }

    private fun leaveBattleFragment() {
        val main = (activity as FullscreenActivity)
        val rewardCard: Card? = main.randomCard(player)
        val victoryFragment = VictoryFragment(rewardCard, goldDropped, player)
        main.replaceCurrentFragmentNoSave(victoryFragment)
    }

    private fun leaveGame() {
        val main = (activity as FullscreenActivity)
        main.createNewGame()
    }

    private fun animatePlayer() {
        CoroutineScope(Dispatchers.Main).launch {
            playerRight.setGuidelinePercent(0.55F)
            playerTop.setGuidelinePercent(0.31F)
            playerLeft.setGuidelinePercent(0.2F)
            playerBottom.setGuidelinePercent(0.56F)

            withContext(Dispatchers.IO) {
                delay(150)
            }

            playerRight.setGuidelinePercent(0.45F)
            playerTop.setGuidelinePercent(0.41F)
            playerLeft.setGuidelinePercent(0.1F)
            playerBottom.setGuidelinePercent(0.66F)
        }
    }

    private fun animateEnemy() {
        val enemyHasMove = enemyAction.childCount > 0
        if (enemyHasMove) {
            CoroutineScope(Dispatchers.Main).launch {
                enemyRight.setGuidelinePercent(0.8F)
                enemyTop.setGuidelinePercent(0.23F)
                enemyLeft.setGuidelinePercent(0.39F)
                enemyBottom.setGuidelinePercent(0.51F)

                withContext(Dispatchers.IO) {
                    delay(150)
                }

                enemyRight.setGuidelinePercent(0.9F)
                enemyTop.setGuidelinePercent(0.13F)
                enemyLeft.setGuidelinePercent(0.49F)
                enemyBottom.setGuidelinePercent(0.41F)
            }
        }
    }

    //Battle Functions

    private fun newTurn() {
        val main = activity as FullscreenActivity
        turnEngine.enemyEnergyIncrease()
        turnEngine.startTurn()
        if (checkBattleState()) cardButtonBuilder(player.playerHand)
        updateEnemyHealth()
        updateEnemyMoves()
        updatePlayerEnergyText()
        updatePlayerBlock()
        updatePlayerAttack()
        main.updatePlayerInDatabase(player)
    }

    private fun useCard(card: Card): Boolean {
        val playable = turnEngine.checkPlayerEnoughEnergy(card)
        if (playable) {
            val damageDone = turnEngine.playCard(card)
            animatePlayer()
            val textToBe = when {
                damageDone == null -> ""
                damageDone != 0 -> "${damageDone * -1}"
                else -> "Blocked"
            }
            val animation = AnimationUtils.loadAnimation(this.context, R.anim.text_slide_up)
            val damage = TextView(this.context).apply {
                text = textToBe
                setTextColor(Color.parseColor("#ff0000"))
                textSize = 42.0F
                textAlignment = TEXT_ALIGNMENT_CENTER
                typeface = Typeface.DEFAULT_BOLD
            }
            damage.startAnimation(animation)
            enemyDameDoneLayout.addView(damage)
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    delay(150)
                }
                enemyDameDoneLayout.removeView(damage)
            }


            updateEnemyMoves(false)
            updateEnemyHealth()
            updatePlayerHealth()
            updatePlayerEnergyText()
            updatePlayerAttack()
            updatePlayerBlock()
            checkBattleState()
        }
        return playable
    }

    private fun endTurn() {
        val damageDone = turnEngine.endTurn()
        updatePlayerHealth()
        updatePlayerEnergyText()
        updateEnemyHealth()
        updatePlayerAttack()
        updatePlayerBlock()
        if (damageDone != null) {
            animateEnemy()
            val textToBe = if(damageDone == 0){
                "Blocked"
            } else {
                "${damageDone * -1}"
            }
            val animation = AnimationUtils.loadAnimation(this.context, R.anim.text_slide_up)
            val damage = TextView(this.context).apply {
                text = textToBe
                setTextColor(Color.parseColor("#ff0000"))
                textSize = 39.0F
                textAlignment = TEXT_ALIGNMENT_CENTER
                typeface = Typeface.DEFAULT_BOLD
            }
            damage.startAnimation(animation)
            playerDameDoneLayout.addView(damage)
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    delay(50)
                }
                playerDameDoneLayout.removeView(damage)
            }
        }
        newTurn()
    }

    private fun checkBattleState(): Boolean {
        when (turnEngine.battleState) {
            BATTLE_LOST -> {
                showDefeatLayout()
                return false
            }
            BATTLE_WON -> {
                player.tempBlock = 0
                player.tempAttack = 0
                leaveBattleFragment()
                return false
            }
        }
        return true
    }

    private fun showDefeatLayout() {
        val defeatLayout = inflater.inflate(R.layout.defeat_fragment_layout, mainLayout, false)
        val defeatButton = defeatLayout.findViewById<Button>(R.id.defeat_button)
        defeatButton.setOnClickListener {
            leaveGame()
        }
        val animationFlashIn = AnimationUtils.loadAnimation(this.context, R.anim.defeat_flash_in).apply {
            setAnimationListener(
                object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {
                        return
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        return
                    }

                    override fun onAnimationStart(animation: Animation?) {
                        return
                    }

                }
            )
        }
        mainLayout.addView(defeatLayout)
        defeatLayout.startAnimation(animationFlashIn)

    }

}