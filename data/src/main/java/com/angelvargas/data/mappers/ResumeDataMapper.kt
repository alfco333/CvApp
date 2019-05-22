package com.angelvargas.data.mappers

import com.angelvargas.cvapp.domain.models.*
import com.angelvargas.data.models.Basics
import com.angelvargas.data.models.Profiles
import com.angelvargas.data.models.Skills
import com.angelvargas.data.models.Work
import com.angelvargas.data.remote.ResumeResponse

class ResumeDataMapper: Transform<ResumeResponse, ResumeData>() {

    override fun transform(value: ResumeResponse): ResumeData {
        val basicsMapper = BasicsDataMapper()
        val workMapper = WorkDataMapper()
        val skillsMapper = SkillsDataMapper()
        return ResumeData(
                value.basics?.let { basicsMapper.transform(it) },
                value.work?.let { workMapper.transformCollection(it) },
                value.skills?.let { skillsMapper.transformCollection(it) })
    }

    inner class BasicsDataMapper: Transform<Basics, BasicsData>() {
        override fun transform(value: Basics): BasicsData {
            val profilesMapper = ProfilesDataMapper()
            return BasicsData(value.name,
                    value.label,
                    value.picture,
                    value.email,
                    value.summary,
                    profilesMapper.transformCollection(value.profiles ?: mutableListOf()))
        }
    }

    inner class ProfilesDataMapper: Transform<Profiles, ProfileData>() {

        override fun transform(value: Profiles): ProfileData {
            return ProfileData(value.network, value.username, value.url)
        }
    }

    inner class WorkDataMapper: Transform<Work, WorkData>() {

        override fun transform(value: Work): WorkData {
            return WorkData(value.company,
                    value.position,
                    value.urlImage,
                    value.startDate,
                    value.endDate,
                    value.summary,
                    value.highlights)
        }
    }

    inner class SkillsDataMapper: Transform<Skills, SkillsData>() {

        override fun transform(value: Skills): SkillsData {
            return SkillsData(value.name, value.level, value.keywords)
        }
    }
}