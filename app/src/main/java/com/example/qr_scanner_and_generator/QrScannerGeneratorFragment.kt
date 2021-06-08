package com.example.qr_scanner_and_generator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_qr_scanner_generator.*

class QrScannerGeneratorFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qr_scanner_generator,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vpQr.adapter= PagerAdapter(this)
        TabLayoutMediator(tlQr,vpQr){ tab, position ->
            if(position==0){
                tab.text="Scanner"
            }
            else if (position==1){
                tab.text="Generator"
            }
        }.attach()
        super.onViewCreated(view, savedInstanceState)
    }
}