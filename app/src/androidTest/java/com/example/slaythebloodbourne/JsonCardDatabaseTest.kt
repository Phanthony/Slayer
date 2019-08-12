package com.example.slaythebloodbourne

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.core.app.ApplicationProvider
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.example.slaythebloodbourne.Entities.Items.Cards.Card_Strike
import com.example.slaythebloodbourne.Modules.PathWayDAO
import com.example.slaythebloodbourne.Modules.PathWayDatabase
import com.example.slaythebloodbourne.Modules.PathWayEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class JsonCardDatabaseTest {
    private lateinit var mDB: PathWayDatabase
    private lateinit var mPlayer: Character
    private lateinit var mCard: Card_Strike
    private lateinit var mDAO: PathWayDAO

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mDB = Room.inMemoryDatabaseBuilder(context, PathWayDatabase::class.java).allowMainThreadQueries()
            .fallbackToDestructiveMigration().build()
        mPlayer = Character()
        mCard = Card_Strike(mPlayer)
        mDAO = mDB.pathwayDAO()
    }

    @After
    fun end() {
        mDB.clearAllTables()
        mDB.close()
    }

    @Test
    fun testCardDesc() {
        val cardList = arrayListOf<Card>(mCard)
        val emptyCardList = arrayListOf<Card>()
        val emptyIntList = arrayListOf<Int>()
        mDAO.insertPathway(PathWayEntity(1, emptyIntList, mPlayer, 1, cardList, emptyCardList, emptyCardList, 1))
        Log.i("CARD", mCard.getDesc())
        Log.i("CARD", mCard.description)
        val newPlayer = mDAO.getPathway()!!.player.apply { tempAttack = 10 }
        val newCard = mDAO.getPathway()!!.playerHand.first().apply { player = newPlayer; description = getDesc() }
        Log.i("NEW CARD", newCard.description)
        newPlayer.tempAttack = 1
        Log.i("NEW CARD", newCard.description)

    }
}