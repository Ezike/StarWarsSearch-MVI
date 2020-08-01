package com.ezike.tobenna.starwarssearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ezike.tobenna.starwarssearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
