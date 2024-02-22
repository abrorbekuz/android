package com.example.notification_otp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.notification_otp.R
import com.example.notification_otp.adapter.RvAdapter
import com.example.notification_otp.databinding.ActivityLogBinding

@Suppress("UNCHECKED_CAST", "UNREACHABLE_CODE")
class LogActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLogBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.notification_otp.R.menu.filter_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    val totallog = arrayListOf(
        "totallog",
        "totallog",
        "totallog",
        "totallog",
        "totallog",
        "totallog",
        "totallog",
        "totallog"
    )
    val todaylog = arrayListOf(
        "todaylog",
        "todaylog",
        "todaylog",
        "todaylog",
        "todaylog",
        "todaylog"
    )

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.today_log -> {
                val adapter = RvAdapter(todaylog)
                binding.adpter.adapter = adapter
            }

            R.id.total_log -> {
                val adapter = RvAdapter(totallog)
                binding.adpter.adapter = adapter
            }
        }
        return super.onOptionsItemSelected(item)
    }
}