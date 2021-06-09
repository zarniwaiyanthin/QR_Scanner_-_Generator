package com.example.qr_scanner_and_generator


import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidmads.library.qrgenearator.QRGSaver
import androidx.fragment.app.Fragment
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.fragment_generator.*
import java.io.File
import java.nio.file.Path

@Suppress("DEPRECATION")
class GeneratorFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_generator,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var str:String=""
        var bitmap:Bitmap?=null

        btnGQR.setOnClickListener {

            str=etTxt2QR.text.toString()
            if (str==""){
                Toast.makeText(context, "Please input text", Toast.LENGTH_SHORT).show()
            }else{
                val qrgEncoder=QRGEncoder(str,null,QRGContents.Type.TEXT,1000)
                try {
                    bitmap=qrgEncoder.bitmap
                    ivQR.setImageBitmap(bitmap)
                }catch(e:WriterException){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        btnS2D.setOnClickListener {
            val dir=File(Environment.getExternalStorageDirectory(),"QR Codes")
            val path= "$dir/"
            if(!dir.exists()){
                dir.mkdir()
            }
            val qrgSaver=QRGSaver()
            if (bitmap==null && str==""){
                Toast.makeText(context, "Please make a QR Code", Toast.LENGTH_SHORT).show()
            }else{
                qrgSaver.save(path,str,bitmap,QRGContents.ImageType.IMAGE_JPEG)
                Toast.makeText(context, "Saved to $path", Toast.LENGTH_LONG).show()
            }
        }
    }
}