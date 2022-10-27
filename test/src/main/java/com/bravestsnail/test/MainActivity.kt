package com.bravestsnail.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bravestsnail.router.annotation.Router

@Router("/test1")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}