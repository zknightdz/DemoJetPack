package com.gvn.demojetpack.ui.test_motion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.gvn.demojetpack.R


class TestMotionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_motion)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_layout, menu)
        return true
    }
}
