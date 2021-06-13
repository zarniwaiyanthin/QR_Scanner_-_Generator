package com.example.qr_scanner_and_generator

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.SparseArray
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.vision.barcode.Barcode
import info.bideens.barcode.BarcodeReader
import java.util.regex.Pattern

class GoogleActivity:AppCompatActivity(), BarcodeReader.BarcodeReaderListener {
    companion object{
        fun newIntent(context: Context):Intent{
            return (Intent(context,GoogleActivity::class.java))
        }
    }
    private lateinit var barcodeReader: BarcodeReader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google)
        Log.d("google","Scan Start")
        barcodeReader=supportFragmentManager.findFragmentById(R.id.barcode_fragment) as BarcodeReader
    }
    override fun onBitmapScanned(sparseArray: SparseArray<Barcode>?) {
        Log.d("google","Bitmap")
    }

    override fun onScannedMultiple(barcodes: MutableList<Barcode>?) {
        Log.d("google","Multi Scan")
    }

    override fun onCameraPermissionDenied() {
        Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
    }

    override fun onScanned(barcode: Barcode?) {
        Log.d("google",barcode?.displayValue?:"Empty")
        barcodeReader.playBeep()
        val result=barcode?.displayValue?:"N/A"
        if (!isLink(result)){
            TextActivity.string=barcode?.displayValue?:"N/A"
            startActivity(TextActivity.newIntent(this))
            finish()
        }
    }

    override fun onScanError(errorMessage: String?) {
        Log.d("google",errorMessage?:"Unknown")
    }

    private fun isLink(test:String):Boolean{
        var bool=true
        val urlCheck="^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$"
        val p= Pattern.compile(urlCheck)
        val m=p.matcher(test)
        if (m.find()){
            val browserIntent=Intent(Intent.ACTION_VIEW, Uri.parse(test))
            startActivity(browserIntent)
            finish()
        }else{
            bool=false
        }
        return bool
    }

}