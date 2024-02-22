package com.example.notification_otp

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.notification_otp.databinding.ActivityLoginBinding
import com.example.notification_otp.ui.DashbordActivity


class LoginActivity : AppCompatActivity() {

    private val AUTH_URL = "http://192.168.1.106/login/"
    private lateinit var myPrefs: SharedPreferences
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        checkPermissions()

        val apiKey = myPrefs.getString("api_key", null)
        if (apiKey != null) {
            startServices()
            val intent = Intent(this@LoginActivity, DashbordActivity::class.java)
            startActivity(intent)
        } else {
            binding.startServer.setOnClickListener {
                login()
            }
        }

    }

    private fun login() {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(this, Uri.parse(AUTH_URL))
    }

    private fun startServices() {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)

        val serviceIntent2 = Intent(this, MFetcher::class.java)
        startService(serviceIntent2)
    }

    override fun onResume() {
        super.onResume()
        if (intent?.data != null && intent.data?.scheme == "login" && intent.data?.host == "apikey") {
            val apiKey = intent.data?.getQueryParameter("key")
            if (apiKey != null) {
                val prefsEditor: SharedPreferences.Editor = myPrefs.edit()
                prefsEditor.putString("api_key", apiKey).apply()
                val intent = Intent(this@LoginActivity, DashbordActivity::class.java)
                startActivity(intent)
            }
        }
    }


    override fun onDestroy() {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        stopService(serviceIntent)

        val serviceIntent2 = Intent(this, MFetcher::class.java)
        stopService(serviceIntent2)

        super.onDestroy()
    }

    private fun checkPermissions() {
        // POST NOTIFICATIONS
        val permissionState =
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        if (permissionState == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }
    }

}