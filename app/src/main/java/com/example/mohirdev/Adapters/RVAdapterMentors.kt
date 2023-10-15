package com.example.mohirdev.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mohirdev.R
import com.example.mohirdev.models.Mentor
import com.squareup.picasso.Picasso

class RVAdapterMentors(var list:ArrayList<Mentor>): RecyclerView.Adapter<RVAdapterMentors.MyVH>() {
  inner class MyVH(var view: View):RecyclerView.ViewHolder(view){
        fun onBind(mentor: Mentor) {
            var image=view.findViewById<ImageView>(R.id.image_v)
            Picasso.get()
                .load(mentor.image)
                .into(image)

        }
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)

        return MyVH(view)
    }

    override fun onBindViewHolder(holder: MyVH, position: Int) {
         holder.onBind(list[position])
    }

    override fun getItemCount(): Int =list.size
}
