package com.example.qr_scanner_and_generator

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidmads.library.qrgenearator.QRGSaver
import androidx.appcompat.app.AppCompatActivity
import com.example.qr_scanner_and_generator.GeneratorFragment.Companion.text
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.activity_image.*
import java.io.File

@Suppress("DEPRECATION")
class ImageActivity:AppCompatActivity() {

    private var bitmap: Bitmap? = null

    companion object{
        fun newIntent(context: Context):Intent{
            return Intent(context,ImageActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

 //       val text=Intent().extras?.getString("text")?:"N/A"
        Log.d("xnxx", text )

        ivQR.setImageBitmap(generateQR(text))

        fabSave.setOnClickListener {
            if (bitmap!=null){
                saveQR(text)
            }else{
                Toast.makeText(this, "Please make a QR Code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateQR(str:String):Bitmap?{
        val qrgEncoder = QRGEncoder(str, null, QRGContents.Type.TEXT, 720)
        try {
            bitmap = qrgEncoder.bitmap
        } catch (e: WriterException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
        return bitmap
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