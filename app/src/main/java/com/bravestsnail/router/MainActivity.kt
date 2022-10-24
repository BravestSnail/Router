package com.bravestsnail.router

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bravestsnail.annotation.Router

@Router("/main")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}