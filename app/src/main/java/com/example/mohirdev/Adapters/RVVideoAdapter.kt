package com.example.mohirdev.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mohirdev.databinding.ItemVideoBinding
import com.example.mohirdev.models.Mentor
import com.example.mohirdev.models.Video
import com.squareup.picasso.Picasso

class RVVideoAdapter(
    var list: ArrayList<Video>,
    var mentor: Mentor,
    var listener: ItemVideoSelectedListener
) : RecyclerView.Adapter<RVVideoAdapter.MyViewHolder>() {
    inner class MyViewHolder(var binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(video: Video) {
            // Picasso.get().load(video.image).into(image)
            binding.titleVideo.text = video.title
            Picasso.get().load(mentor.image).into(binding.profileImg)
            Picasso.get().load(video.image).into(binding.imgVideo)
            binding.playBtn.setOnClickListener {
                listener.onItemVideoSelected(video)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RVVideoAdapter.MyViewHolder =
        MyViewHolder(ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RVVideoAdapter.MyViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}

interface ItemVideoSelectedListener {
    fun onItemVideoSelected(video: Video)
}
