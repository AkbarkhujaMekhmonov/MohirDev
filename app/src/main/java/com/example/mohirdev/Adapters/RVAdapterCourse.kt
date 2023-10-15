package com.example.mohirdev.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mohirdev.databinding.ItemCourseBinding
import com.example.mohirdev.models.Course
import com.example.mohirdev.models.Mentor
import com.squareup.picasso.Picasso


class RVAdapterCourse(
    var list: ArrayList<Course>,
    var mentor: ArrayList<Mentor>,
    var listener: OnItemCourseselectListener
) :
    RecyclerView.Adapter<RVAdapterCourse.MyViewHolder>() {
    inner class MyViewHolder(var binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(course: Course) {
            var mentor1=Mentor()
            Picasso.get()
                .load(course.image)
                .into(binding.iconLang)
            mentor.forEach {
                if (course.mentorID == it.ID) {
                    binding.mentorName1.text = it.name
                    mentor1 = it
                }
            }
            binding.root.setOnClickListener {
                listener.onItemCourseselect(course,mentor1)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RVAdapterCourse.MyViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}

interface OnItemCourseselectListener {
    fun onItemCourseselect(course: Course,mentor:Mentor)
}
