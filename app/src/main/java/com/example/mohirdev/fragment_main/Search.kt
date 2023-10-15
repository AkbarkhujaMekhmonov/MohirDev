package com.example.mohirdev.fragment_main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mohirdev.R
import com.example.mohirdev.databinding.FragmentSearchBinding
import com.google.firebase.database.*


class Search : Fragment() {
    private lateinit var database: DatabaseReference
    lateinit var binding:FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance().reference

        binding.SearchBtn.setOnClickListener{
            searchData(binding.searchIn.text.toString())
            Toast.makeText(binding.root.context, "tap", Toast.LENGTH_SHORT).show()
        }

        // Inflate the layout for this fragment
        return binding.root
    }


    private fun searchData(searchQuery: String) {
        val query: com.google.firebase.database.Query = database.child("Courses")
            .orderByChild("title")
            .equalTo(searchQuery)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Clear previous search results
                // ...

                // Iterate through the search results
                for (snapshot in dataSnapshot.children) {
                    // Extract the data
                    val result = snapshot.getValue(com.example.mohirdev.models.Course::class.java)
                    Toast.makeText(binding.root.context, "yes", Toast.LENGTH_SHORT).show()
                    // Process the result
                    // ...
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error
                // ...
            }
        })
    }


}