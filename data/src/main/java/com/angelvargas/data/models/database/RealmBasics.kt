package com.angelvargas.data.models.database

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class RealmBasics(): RealmObject() {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var name: String = ""
    var label: String = ""
    var picture: String = ""
    var email: String = ""
    var summary: String = ""
    var profiles: RealmList<RealmProfile> = RealmList()

    constructor(name: String,
                label: String,
                picture: String,
                email: String,
                summary: String,
                profiles: RealmList<RealmProfile>): this() {
        this.name = name
        this.label = label
        this.picture = picture
        this.email = email
        this.summary = summary
        this.profiles = profiles
    }
}