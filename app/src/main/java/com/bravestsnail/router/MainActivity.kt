package com.bravestsnail.router

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bravestsnail.annotation.Router
import com.bravestsnail.routerhandler.RouterUtils
import com.bravestsnail.routerhandler.generated.RouterInit

@Router("/main")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RouterUtils.init(RouterInit)
        findViewById<Button>(R.id.test).setOnClickListener {
            RouterUtils.dispatch(this, "/test")
        }
    }
}