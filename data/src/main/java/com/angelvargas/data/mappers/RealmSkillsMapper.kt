package com.angelvargas.data.mappers

import com.angelvargas.data.models.Skills
import com.angelvargas.data.models.database.RealmSkills
import io.realm.RealmList

class RealmSkillsMapper: Transform<Skills, RealmSkills>() {

    override fun transform(value: Skills): RealmSkills {
        return RealmSkills(value.name ?: "",
            value.level ?: "",
            RealmList(*(value.keywords ?: emptyList()).toTypedArray()))
    }
}