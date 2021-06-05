package com.example.qr_scanner_and_generator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.SparseArray
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.vision.barcode.Barcode
import info.bideens.barcode.BarcodeReader

class GoogleActivity:AppCompatActivity(), BarcodeReader.BarcodeReaderListener {
    companion object{
        var code:String?=null
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
        code =barcode?.displayValue
        val intent=MainActivity.newIntent(this)
        startActivity(intent)
        finish()

    }

    override fun onScanError(errorMessage: String?) {
        Log.d("google",errorMessage?:"Unknown")
    }

}