package com.example.qr_scanner_and_generator

import android.app.Activity
import android.app.Notification
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidmads.library.qrgenearator.QRGSaver
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.qr_scanner_and_generator.Util.bitmap
import com.example.qr_scanner_and_generator.Util.string
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.fragment_image.*
import java.io.File
import java.util.*

@Suppress("DEPRECATION")
class ImageFragment:DialogFragment() {

    private var resultUri:Uri?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.image_background)
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivQR.setImageBitmap(bitmap)

        fabSave.setOnClickListener {
            if (bitmap!=null){
                saveQR(string)
            }else{
                Toast.makeText(context, "Please make a QR Code", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveQR(text:String?){
        val dir= if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
               File(context?.getExternalFilesDir(null),"QR Codes")
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
        Toast.makeText(context, "Saved to $path", Toast.LENGTH_LONG).show()
    }

    private fun galleryAddPic(f:File){
        MediaScannerConnection.scanFile(context, arrayOf(f.toString()), null, null)
    }
}