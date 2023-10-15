package com.example.mohirdev.fragment_main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mohirdev.Adapters.OnItemCourseselectListener
import com.example.mohirdev.Adapters.RVAdapterCourse
import com.example.mohirdev.Adapters.RVAdapterMentors
import com.example.mohirdev.R
import com.example.mohirdev.databinding.FragmentCoursesBinding
import com.example.mohirdev.models.Course
import com.example.mohirdev.models.Mentor
import com.google.firebase.firestore.FirebaseFirestore


class Courses : Fragment() {
    lateinit var binding: FragmentCoursesBinding
    lateinit var firestore: FirebaseFirestore
    lateinit var listOfCourse: ArrayList<Course>
    lateinit var listOfMentor: ArrayList<Mentor>
    private  val TAG = "Courses"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoursesBinding.inflate(layoutInflater)
        firestore = FirebaseFirestore.getInstance()
        listOfCourse= ArrayList()
        listOfMentor=ArrayList()

        firestore.collection("Mentors").get().addOnCompleteListener {
            if (it.isSuccessful) {
                var result = it.result
                result.forEach { queryDocumentSnapshot ->
                    val mentorn = queryDocumentSnapshot.toObject(Mentor::class.java)
                    listOfMentor.add(mentorn)
                }
                Log.d(TAG, "onCreateView:$listOfMentor ")
                firestore.collection("Courses").get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        var result = it.result
                        result.forEach { queryDocumentSnapshot ->
                            val cor = queryDocumentSnapshot.toObject(Course::class.java)
                            listOfCourse.add(cor)
                        }
                        binding.rvCourse.adapter=RVAdapterCourse(listOfCourse,listOfMentor,listener)

                    }
                }

            }
        }



        return binding.root
    }

    var listener=object :OnItemCourseselectListener{
        override fun onItemCourseselect(course: Course,mentor:Mentor) {
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container, Course(course,mentor)).commit()
        }
    }

}