package com.ezike.tobenna.starwarssearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ezike.tobenna.starwarssearch.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
