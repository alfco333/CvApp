package com.angelvargas.data.mappers

import com.angelvargas.cvapp.domain.models.*
import com.angelvargas.data.models.database.*

class ResumeRealmDataMapper: Transform<RealmResume, ResumeData> (){

    override fun transform(value: RealmResume): ResumeData {

        return ResumeData(
            value.basics?.let { BasicsRealmDataMapper().transform(it) },
            WorkRealmDataMapper().transformCollection(value.work.toList()),
            SkillsRealmDataMapper().transformCollection(value.skills.toList()))
    }

    class BasicsRealmDataMapper: Transform<RealmBasics, BasicsData>() {

        override fun transform(value: RealmBasics): BasicsData {
            return BasicsData(value.name,
                value.label,
                value.picture,
                value.email,
                value.summary,
                ProfileRealmDataMapper().transformCollection(value.profiles.toList()))
        }
    }

    class ProfileRealmDataMapper: Transform<RealmProfile, ProfileData>() {

        override fun transform(value: RealmProfile): ProfileData {
            return ProfileData(value.network, value.username, value.url)
        }
    }

    class WorkRealmDataMapper: Transform<RealmWork, WorkData>() {

        override fun transform(value: RealmWork): WorkData {
            return WorkData(value.company,
                value.position,
                value.urlImage,
                value.startDate,
                value.endDate,
                value.summary,
                emptyList())
        }
    }

    class SkillsRealmDataMapper: Transform<RealmSkills, SkillsData>() {

        override fun transform(value: RealmSkills): SkillsData {
            return SkillsData(value.name,
                value.level,
                value.keywords.toList())
        }
    }
}