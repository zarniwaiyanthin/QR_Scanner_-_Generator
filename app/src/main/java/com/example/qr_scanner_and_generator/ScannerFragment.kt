package com.example.qr_scanner_and_generator

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.qr_scanner_and_generator.GoogleActivity.Companion.code
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_scanner.*
import java.io.FileNotFoundException
import java.util.regex.Pattern

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
            if (!isLink(code.toString())){
                tvScanResult.text= code
            }
        }

        btnSFG.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK)
            intent.type="image/*"
//            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
            startActivityForResult(intent,1111)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data)

        if (intentResult!=null){
            if (intentResult.contents==null){
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
            }else{
                if (!isLink(intentResult.contents)){
                    tvScanResult.text=intentResult.contents
                    tvFormat.text=intentResult.formatName
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }

        if (resultCode!=Activity.RESULT_CANCELED){
            if(requestCode==1111){
                decodeQR(data)
            }
        }
    }

    private fun decodeQR(data: Intent?){
        if (data==null || data?.data==null){
            Log.d("tag","The uri is null, probably the user cancelled the image selection process using the back button.")
        }

        val  uri= data?.data

            val getContentResolver=context?.contentResolver
            val inputStream= uri?.let { getContentResolver?.openInputStream(it) }
            val bitmap=BitmapFactory.decodeStream(inputStream)

            if (bitmap==null){
                Log.d("TAG", "uri is not a bitmap," + uri.toString())
            }

            val width=bitmap.width
            val height=bitmap.height
            val pixels= IntArray(width*height)

            try {
                bitmap.getPixels(pixels,0,width,0,0,width,height)
                bitmap.recycle()
                val source=RGBLuminanceSource(width,height,pixels)
                val bBitmap=BinaryBitmap(HybridBinarizer(source))
                val reader=MultiFormatReader()

                try{

                    val decode=reader.decode(bBitmap)
                    val result=decode.text
                    if (!isLink(result)){
                        tvScanResult.text=result
                    }

                }catch (e:NotFoundException){
                    Log.d("TAG", "decode exception", e)
                }

            }catch (e:FileNotFoundException){
                Log.e("TAG", "can not open file" + uri.toString(), e);
            }
    }

    private fun isLink(test:String):Boolean{
        var bool=true
        val urlCheck="^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$"
        val p=Pattern.compile(urlCheck)
        val m=p.matcher(test)
        if (m.find()){
            val browserIntent=Intent(Intent.ACTION_VIEW, Uri.parse(test))
            startActivity(browserIntent)
        }else{
            bool=false
        }
        return bool
    }
}