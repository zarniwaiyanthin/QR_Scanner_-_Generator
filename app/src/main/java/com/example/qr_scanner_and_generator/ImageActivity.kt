package com.example.qr_scanner_and_generator

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
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
        val dir= if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            File(getExternalFilesDir(null),"QR Codes")
        }else{
            File(Environment.getExternalStorageDirectory(),"QR Codes")
        }
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
        MediaScannerConnection.scanFile(this, arrayOf(f.toString()),null,null)
    }
}