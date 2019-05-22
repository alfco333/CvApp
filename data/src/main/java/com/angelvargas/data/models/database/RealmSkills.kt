package com.angelvargas.data.models.database

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class RealmSkills(): RealmObject() {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var name : String = ""
    var level : String = ""
    @Required
    var keywords : RealmList<String> = RealmList()

    constructor(name: String, level: String, keywords: RealmList<String>): this() {
        this.name = name
        this.level = level
        this.keywords = keywords
    }
}