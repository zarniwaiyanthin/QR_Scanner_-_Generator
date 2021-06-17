package com.example.qr_scanner_and_generator

import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_text.*

class TextFragment:DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        dialog!!.setTitle("Decoded Text")
        return inflater.inflate(R.layout.fragment_text,container,false)
    }

    override fun onStart() {
        super.onStart()
        val width=(resources.displayMetrics.widthPixels*0.85).toInt()
        val height=(resources.displayMetrics.widthPixels*0.50).toInt()
        dialog!!.window?.setLayout(width,ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text=Util.string
        tvText.text=text
        btnCopy.setOnClickListener {
            copyToClipBoard(text)
        }
    }

    private fun copyToClipBoard(text:String?){
        val clipboard= context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip= ClipData.newPlainText("QR",text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Copied to Clipboard", Toast.LENGTH_SHORT).show()
    }
}