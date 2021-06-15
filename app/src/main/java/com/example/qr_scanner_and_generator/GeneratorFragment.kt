package com.example.qr_scanner_and_generator


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment.*
import android.view.*
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidmads.library.qrgenearator.QRGSaver
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.fragment_generator.*
import java.io.File

@Suppress("DEPRECATION")
class GeneratorFragment: Fragment() {
    companion object{
        var bitmap: Bitmap? = null
        var text:String=""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_generator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnGQR.setOnClickListener {

            val str = etTxt2QR.text.toString()
            if (str == "") {
                Toast.makeText(context, "Please input text", Toast.LENGTH_SHORT).show()
            } else {
                 generateQR(str)
                 val intent= context?.let { context -> ImageActivity.newIntent(context) }
                intent?.putExtra("text",str)
                startActivity(intent)
            }
        }
    }

    private fun generateQR(str:String):Bitmap?{
        val qrgEncoder = QRGEncoder(str, null, QRGContents.Type.TEXT, 720)
        try {
            bitmap = qrgEncoder.bitmap
            text=str
        } catch (e: WriterException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
        return bitmap
    }

}