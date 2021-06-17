package com.example.qr_scanner_and_generator

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    companion object{
        fun newIntent(context: Context):Intent{
            return Intent(context,MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerMain,QrScannerGeneratorFragment())
            .commit()
    }
}