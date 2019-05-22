package com.angelvargas.data.models.database

import io.realm.RealmObject
import java.util.*

open class RealmProfile(): RealmObject() {
    var id: String = UUID.randomUUID().toString()
    var network: String = ""
    var username: String = ""
    var url: String = ""

    constructor(network: String, userName: String, url: String): this() {
        this.network = network
        this.username = userName
        this.url = url
    }
}