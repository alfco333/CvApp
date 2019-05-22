package com.angelvargas.data.mappers

import com.angelvargas.data.models.Work
import com.angelvargas.data.models.database.RealmWork
import io.realm.RealmList

class RealmWorkMapper: Transform<Work, RealmWork>() {

    override fun transform(value: Work): RealmWork {
        val highlightsArray: Array<String> = (value.highlights ?: emptyList()).toTypedArray()
        val highlightsRealmList = RealmList<String>(*highlightsArray)
        return RealmWork(value.company ?: "",
            value.position ?: "",
            value.urlImage ?: "",
            value.startDate ?: "",
            value.endDate ?: "",
            value.summary ?: "",
            highlightsRealmList)
    }
}