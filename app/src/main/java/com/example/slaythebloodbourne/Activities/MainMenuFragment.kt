package com.example.slaythebloodbourne.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.slaythebloodbourne.R

class MainMenuFragment:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.mainscreen_fragment_layout,container,false)
        val startButton = view.findViewById<Button>(R.id.gameStartButton)

        startButton.setOnClickListener {
            val newGame = PathwayFragment()
            val mainActivity = activity as FullscreenActivity
            mainActivity.addFragment(newGame)
            mainActivity.changeFragment(1)
        }

        return view
    }
}