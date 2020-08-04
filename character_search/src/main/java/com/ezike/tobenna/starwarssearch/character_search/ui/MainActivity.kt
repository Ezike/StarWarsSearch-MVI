package com.ezike.tobenna.starwarssearch.character_search.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ezike.tobenna.starwarssearch.character_search.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
