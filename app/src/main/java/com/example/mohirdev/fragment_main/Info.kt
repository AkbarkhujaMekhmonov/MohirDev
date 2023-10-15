package com.example.mohirdev.fragment_main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mohirdev.R
import com.example.mohirdev.databinding.FragmentInfoBinding


class Info : Fragment() {
lateinit var binding: FragmentInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentInfoBinding.inflate(layoutInflater)
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

}