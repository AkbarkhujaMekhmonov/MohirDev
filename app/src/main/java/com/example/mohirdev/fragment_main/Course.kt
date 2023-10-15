package com.example.mohirdev.fragment_main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mohirdev.Adapters.ItemVideoSelectedListener
import com.example.mohirdev.Adapters.RVVideoAdapter
import com.example.mohirdev.VideoPlayeractivity
import com.example.mohirdev.databinding.FragmentCourseBinding
import com.example.mohirdev.models.Mentor
import com.example.mohirdev.models.Video
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject


class Course(var course: com.example.mohirdev.models.Course, var mentor: Mentor) : Fragment() {
    lateinit var binding: FragmentCourseBinding
    lateinit var firestore: FirebaseFirestore
    lateinit var listVideos: ArrayList<Video>
    private val TAG = "Course"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseBinding.inflate(layoutInflater)

        listVideos = ArrayList()
        firestore = FirebaseFirestore.getInstance()
        firestore.collection("Videos").get().addOnCompleteListener {
            if (it.isSuccessful){
                val result=it.result
                result.forEach {queryDocumentSnapshot->
                    val video=queryDocumentSnapshot.toObject(Video::class.java)
                    listVideos.add(video)


                }
                binding.rvVideos.adapter= RVVideoAdapter(listVideos,mentor,listener)
                Log.i(TAG, listVideos.size.toString())

            }
        }



        return binding.root
    }

    var listener = object : ItemVideoSelectedListener {
        override fun onItemVideoSelected(video: Video) {
            var intent = Intent(getActivity(), VideoPlayeractivity::class.java)
            intent.putExtra("video", video)
            startActivity(intent)
        }
    }

}