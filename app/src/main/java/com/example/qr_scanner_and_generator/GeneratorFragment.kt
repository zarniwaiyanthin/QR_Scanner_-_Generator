package com.example.qr_scanner_and_generator


import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.Fragment
import com.example.qr_scanner_and_generator.Util.bitmap
import com.example.qr_scanner_and_generator.Util.string
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.fragment_generator.*

@Suppress("DEPRECATION")
class GeneratorFragment: Fragment() {

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
                 ImageFragment().show(parentFragmentManager,"qr")
            }
        }
    }

    private fun generateQR(str:String):Bitmap?{
        val qrgEncoder = QRGEncoder(str, null, QRGContents.Type.TEXT, 720)
        try {
            bitmap = qrgEncoder.bitmap
            string= str
        } catch (e: WriterException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
        return bitmap
    }

}