package com.example.qr_scanner_and_generator

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidmads.library.qrgenearator.QRGSaver
import androidx.appcompat.app.AppCompatActivity
import com.example.qr_scanner_and_generator.GeneratorFragment.Companion.bitmap
import com.example.qr_scanner_and_generator.GeneratorFragment.Companion.text
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.activity_image.*
import java.io.File

@Suppress("DEPRECATION")
class ImageActivity:AppCompatActivity() {

    companion object{
        fun newIntent(context: Context):Intent{
            return Intent(context,ImageActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_image)

        ivQR.setImageBitmap(bitmap)

        fabSave.setOnClickListener {
            if (bitmap!=null){
                saveQR(text)
            }else{
                Toast.makeText(this, "Please make a QR Code", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun saveQR(text:String){
        val dir = File(Environment.getExternalStorageDirectory(), "QR Codes")
        val path = "$dir/"
        val f = File(path, "$text.jpg")
        galleryAddPic(f)
        if (!dir.exists()) {
            dir.mkdir()
        }
        val qrgSaver = QRGSaver()

        qrgSaver.save(path, text, bitmap, QRGContents.ImageType.IMAGE_JPEG)
        Toast.makeText(this, "Saved to $path", Toast.LENGTH_LONG).show()
    }

    private fun galleryAddPic(f:File){
        this?.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)))
    }
}