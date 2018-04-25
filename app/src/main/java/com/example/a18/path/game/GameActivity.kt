package com.example.a18.path.game

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.a18.path.R
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        game_view.setOnClickListener {
            game_view.sport()
        }
    }
}
