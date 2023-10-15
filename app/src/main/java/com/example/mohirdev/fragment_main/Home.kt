package com.example.mohirdev.fragment_main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.mohirdev.Adapters.RVAdapterMentors
import com.example.mohirdev.R
import com.example.mohirdev.VideoPlayeractivity
import com.example.mohirdev.models.Mentor
import com.example.mohirdev.models.Video
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

@Suppress("CAST_NEVER_SUCCEEDS")
class Home : Fragment() {
    lateinit var rvAdapterMentors: RVAdapterMentors
    lateinit var storage: FirebaseFirestore
    lateinit var listMentor: ArrayList<Mentor>
    lateinit var videoView: VideoView
    lateinit var imageList: ArrayList<String>
    lateinit var listvideo:ArrayList<Video>
    private val TAG = "Home"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home2, container, false)

        val imageVideo=view.findViewById<ImageView>(R.id.img_video)
        val imageSlider=view.findViewById<ImageSlider>(R.id.imageSlider)
        var arraymodel=ArrayList<SlideModel>()
        arraymodel.add(SlideModel(R.drawable.image1,ScaleTypes.FIT))
        arraymodel.add(SlideModel(R.drawable.image2,ScaleTypes.FIT))
        arraymodel.add(SlideModel(R.drawable.image3,ScaleTypes.FIT))
        arraymodel.add(SlideModel(R.drawable.image4,ScaleTypes.FIT))
        imageSlider.setImageList(arraymodel,ScaleTypes.FIT)
        val play=view.findViewById<ImageView>(R.id.play_btn)
        listMentor = ArrayList()
        storage = FirebaseFirestore.getInstance()
        listvideo=ArrayList()

        storage.collection("Mentors").get().addOnCompleteListener {
            if (it.isSuccessful) {
                var result = it.result
                result.forEach { queryDocumentSnapshot ->
                    val mentorn = queryDocumentSnapshot.toObject(Mentor::class.java)
                    listMentor.add(mentorn)
                }
                Log.d(TAG, "onCreateView:$listMentor ")
                rvAdapterMentors = RVAdapterMentors(listMentor)
                var rvTable = view.findViewById<RecyclerView>(R.id.RV)
                rvTable.adapter = rvAdapterMentors
            }
        }
        var videom=Video()
         storage.collection("Videos").get().addOnCompleteListener {
                if (it.isSuccessful){
                    var res=it.result
                    res.forEach { QueryDocumentSnapshot->
                        var video=  QueryDocumentSnapshot.toObject(Video::class.java)
                        if (video.title=="Loyihalar"){
                            videom=video
                        }
                        listvideo.add(video)
                    }
                    play.setOnClickListener {
                        var intent = Intent(activity, VideoPlayeractivity::class.java)
                        intent.putExtra("video", videom)
                        startActivity(intent)
                    }

                    Picasso.get().load(videom.image).into(imageVideo)

                }


        }




        return view
    }


}