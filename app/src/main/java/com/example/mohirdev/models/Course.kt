package com.example.mohirdev.models

class Course {
    var id: Int = 0
    var title: String? = null
    var mentorID:Int = 0
    var image: String?=null



    constructor()
    constructor(id: Int, title: String?, mentorID: Int, image: String?) {
        this.id = id
        this.title = title
        this.mentorID = mentorID
        this.image = image
    }
}