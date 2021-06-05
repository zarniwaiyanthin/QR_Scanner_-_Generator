package com.example.qr_scanner_and_generator

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.qr_scanner_and_generator.GoogleActivity.Companion.code
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_scanner.*

class ScannerFragment :Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scanner,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intentIntegrator=IntentIntegrator.forSupportFragment(this)
        btnZxing.setOnClickListener {
            intentIntegrator.setPrompt("Scan Barcode or QR Code")
            intentIntegrator.setOrientationLocked(false)
            intentIntegrator.initiateScan()
        }
        btnGoogle.setOnClickListener {
            startActivity(context?.let { context -> GoogleActivity.newIntent(context) })
        }
        if (code!=null){
            tvScanResult.text= code
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if (intentResult!=null){
            if (intentResult.contents==null){
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
            }else{
                tvScanResult.text=intentResult.contents
                tvFormat.text=intentResult.formatName
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}