package com.example.mohirdev.fragment_main

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.mohirdev.R
import com.example.mohirdev.databinding.FragmentDashboardScreenBinding

class Dashboard_screen : Fragment() {

    lateinit var binding:FragmentDashboardScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDashboardScreenBinding.inflate(layoutInflater)

        activity?.supportFragmentManager!!.beginTransaction()
            .replace(R.id.fragment_container, Home()).commit()

        binding.home.setOnClickListener{
            clear()
            it.background = ContextCompat.getDrawable(binding.root.context,R.drawable.back_bottom_nav_icons)

            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container, Home()).commit()
        }
        binding.search.setOnClickListener{
            clear()
            it.background = ContextCompat.getDrawable(binding.root.context,R.drawable.back_bottom_nav_icons)

            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container, Search()).commit()
        }
        binding.profile.setOnClickListener{
            clear()
            it.background = ContextCompat.getDrawable(binding.root.context,R.drawable.back_bottom_nav_icons)

            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container, Profil()).commit()
        }
        binding.courses.setOnClickListener{
            clear()
            it.background = ContextCompat.getDrawable(binding.root.context,R.drawable.back_bottom_nav_icons)

            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container, Courses()).commit()
        }
        binding.settings.setOnClickListener{
            clear()
            it.background = ContextCompat.getDrawable(binding.root.context,R.drawable.back_bottom_nav_icons)

            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment()).commit()
        }

        return binding.root
    }

    private fun clear() {
        binding.home.setBackgroundColor(Color.WHITE)
        binding.search.setBackgroundColor(Color.WHITE)
        binding.profile.setBackgroundColor(Color.WHITE)
        binding.courses.setBackgroundColor(Color.WHITE)
        binding.settings.setBackgroundColor(Color.WHITE)
    }

}