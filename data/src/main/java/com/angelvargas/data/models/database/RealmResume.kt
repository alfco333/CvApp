package com.angelvargas.data.models.database

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class RealmResume(): RealmObject() {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var basics: RealmBasics? = null
    var work: RealmList<RealmWork> = RealmList()
    var skills: RealmList<RealmSkills> = RealmList()

    constructor(basics: RealmBasics, works: RealmList<RealmWork>, skills: RealmList<RealmSkills>): this() {
        this.basics = basics
        this.work = works
        this.skills = skills
    }
}