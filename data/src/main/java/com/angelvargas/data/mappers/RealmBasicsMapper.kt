package com.angelvargas.data.mappers

import com.angelvargas.data.models.Basics
import com.angelvargas.data.models.database.RealmBasics
import com.angelvargas.data.models.database.RealmProfile
import io.realm.RealmList

class RealmBasicsMapper: Transform<Basics, RealmBasics>() {

    override fun transform(value: Basics): RealmBasics {
        val profilesArray: Array<RealmProfile> = RealmProfileMapper()
            .transformCollection(value.profiles ?: emptyList())
            .toTypedArray()
        val reamList: RealmList<RealmProfile> = RealmList(*profilesArray)

        return RealmBasics(value.name ?: "",
            value.label ?: "",
            value.picture ?: "",
            value.email ?: "",
            value.summary ?: "",
            reamList)
    }
}