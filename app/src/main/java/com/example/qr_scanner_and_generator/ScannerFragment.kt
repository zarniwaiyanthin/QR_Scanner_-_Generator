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
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesUtil
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

        var userChoice:Int=0

        btnZxing.setOnClickListener {
        //    scanWithZxing()
            scanFromDevice()
        }
        btnGoogle.setOnClickListener {
            scanWithGoogle()
        }

        btnSFG.setOnClickListener {
            scanFromGallery()
        }

        when (rgSelect.checkedRadioButtonId){
            R.id.rbGallery-> Toast.makeText(context, "Gallery", Toast.LENGTH_SHORT).show()
            R.id.rbCamera-> Toast.makeText(context, "Camera", Toast.LENGTH_SHORT).show()
        }

    }

    private fun scanWithZxing(){
        val intentIntegrator=IntentIntegrator.forSupportFragment(this)
        intentIntegrator.setPrompt("Scan Barcode or QR Code")
        intentIntegrator.setOrientationLocked(false)
        intentIntegrator.initiateScan()
    }

    private fun scanWithGoogle(){
        startActivity(context?.let { context -> GoogleActivity.newIntent(context) })
    }

    private fun scanFromDevice(){
        val status=GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        if (status==ConnectionResult.SUCCESS){
    //        scanWithGoogle()
            Toast.makeText(context, "This device has google service.", Toast.LENGTH_SHORT).show()
        }else{
     //       scanWithZxing()
            Toast.makeText(context, "This device has no google service.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun scanFromGallery(){
        val intent=Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,1111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data)

        if (intentResult!=null){
            if (intentResult.contents==null){
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
            }else{
                if (!isLink(intentResult.contents)){
                    TextActivity.string=intentResult.contents
                    startActivity(context?.let { TextActivity.newIntent(it) })
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
                        TextActivity.string=result
                        startActivity(context?.let { TextActivity.newIntent(it) })
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