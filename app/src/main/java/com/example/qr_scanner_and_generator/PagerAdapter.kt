package com.example.qrscannerandgenerator

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.qr_scanner_and_generator.GeneratorFragment
import com.example.qr_scanner_and_generator.ScannerFragment

class PagerAdapter(fragment: Fragment):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if(position==0){
            return ScannerFragment()
        }
        else{
            return GeneratorFragment()
        }
    }
}