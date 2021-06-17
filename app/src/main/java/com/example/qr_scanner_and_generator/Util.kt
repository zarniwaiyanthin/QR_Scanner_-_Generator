package com.example.qr_scanner_and_generator

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import java.util.regex.Pattern

object Util {

    var isGoogle:Boolean=false
    var string:String?=null
    var bitmap: Bitmap? = null

    fun isLink(test:String):Boolean{
        var bool=true
        val urlCheck="^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$"
        val p= Pattern.compile(urlCheck)
        val m=p.matcher(test)
        bool = m.find()
        return bool
    }

    fun openBrowser(text: String,context: Context){
        val browserIntent=Intent(Intent.ACTION_VIEW, Uri.parse(text))
        startActivity(context,browserIntent,null)
    }
}