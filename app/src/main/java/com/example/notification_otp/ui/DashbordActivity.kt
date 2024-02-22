package com.example.notification_otp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.notification_otp.databinding.ActivityDashbordBinding


class DashbordActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDashbordBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.apply {
            val intent = Intent(this@DashbordActivity, LogActivity::class.java)
            totalLog.setOnClickListener {
                startActivity(intent)
            }
            todayLog.setOnClickListener {
                startActivity(intent)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.notification_otp.R.menu.item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}