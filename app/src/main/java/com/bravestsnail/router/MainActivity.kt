package com.bravestsnail.router

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bravestsnail.router.annotation.Router
import com.bravestsnail.router.handler.RouterUtils

@Router("/main")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.test).setOnClickListener {
            RouterUtils.dispatch(this, "/test1")
        }

    }
}