package com.example.slaythebloodbourne.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.slaythebloodbourne.Modules.PathWayDAO
import com.example.slaythebloodbourne.Modules.PathWayDatabase
import com.example.slaythebloodbourne.Modules.PathWayEntity
import com.example.slaythebloodbourne.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.file.Path

class MainMenuFragment(val pathwayDao: PathWayDAO):Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.mainscreen_fragment_layout,container,false)
        val startButton = view.findViewById<Button>(R.id.gameStartButton)

        startButton.setOnClickListener {
            checkSave()
        }

        return view
    }

    fun checkSave(){
        val mainActivity = activity as FullscreenActivity
        CoroutineScope(Dispatchers.IO).launch {
            val game: Fragment
            val previousPathway = pathwayDao.getPathway()
            game = if(previousPathway == null){
                PathwayFragment()
            } else{
                PathwayFragment(previousPathway.roomSelections,
                    previousPathway.player,
                    previousPathway.floorCount,
                    previousPathway.playerDiscard,
                    previousPathway.playerHand,
                    previousPathway.playerDeck)
            }
            mainActivity.replaceCurrentFragmentSave(game)
        }
    }
}