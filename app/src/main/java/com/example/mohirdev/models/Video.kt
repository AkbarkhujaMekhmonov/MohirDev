package com.example.mohirdev.models

class Video :java.io.Serializable{
    var link:String?=null
    var courseID:Int=0
    var image:String?=null
    var mentorID:Int=0
    var title:String?=null

    constructor()
    constructor(link: String?, courseID: Int, image: String?, mentorID: Int, title: String?) {
        this.link = link
        this.courseID = courseID
        this.image = image
        this.mentorID = mentorID
        this.title = title
    }

}
