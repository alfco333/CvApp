package com.angelvargas.data.mappers

import com.angelvargas.data.models.Basics
import com.angelvargas.data.models.database.RealmResume
import com.angelvargas.data.models.database.RealmSkills
import com.angelvargas.data.models.database.RealmWork
import com.angelvargas.data.remote.ResumeResponse
import io.realm.RealmList

class RealmResumeMapper: Transform<ResumeResponse, RealmResume>() {

    override fun transform(value: ResumeResponse): RealmResume {
        val realmWorks: RealmList<RealmWork> = RealmList(*RealmWorkMapper()
            .transformCollection(value.work ?: emptyList())
            .toTypedArray())
        val realmSkills: RealmList<RealmSkills> = RealmList(*RealmSkillsMapper()
            .transformCollection(value.skills ?: emptyList())
            .toTypedArray())
        return RealmResume(RealmBasicsMapper().transform(value.basics
            ?: Basics("", "", "", "", "", emptyList())),
            realmWorks,
            realmSkills)
    }
}