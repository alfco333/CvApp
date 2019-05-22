package com.angelvargas.data.models.database

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class RealmWork(): RealmObject() {
    @PrimaryKey
    var id = UUID.randomUUID().toString()
    var company: String = ""
    var position: String = ""
    var urlImage: String = ""
    var startDate: String = ""
    var endDate: String = ""
    var summary: String = ""
    var highlights: RealmList<String> = RealmList()

    constructor(company: String,
                position: String,
                urlImage: String,
                startDate: String,
                endDate: String,
                summary: String,
                highlights: RealmList<String>): this() {
        this.company = company
        this.position = position
        this.urlImage = urlImage
        this.startDate = startDate
        this.endDate = endDate
        this.summary = summary
        this.highlights = highlights
    }
}