package com.example.qr_scanner_and_generator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_text.*

class TextActivity:AppCompatActivity() {
    companion object{
        var string:String=""
        fun newIntent(context: Context):Intent{
            return Intent(context,TextActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)
        tvText.text= string

        btnCopy.setOnClickListener {
            copyToClipBoard(string)
        }
    }

    private fun copyToClipBoard(text:String){
        val clipboard=getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip=ClipData.newPlainText("QR",text)
        clipboard.setPrimaryClip(clip)
    }
}