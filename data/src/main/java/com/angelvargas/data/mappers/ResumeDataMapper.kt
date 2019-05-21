package com.angelvargas.data.mappers

import com.angelvargas.cvapp.domain.ResumeResponse
import com.angelvargas.cvapp.domain.models.*
import com.angelvargas.data.models.*

class ResumeDataMapper: Transform<ResumeResponse, ResumeData>() {

    override fun transform(value: ResumeResponse): ResumeData {
        val basicsMapper = BasicsDataMapper()
        val workMapper = WorkDataMapper()
        val skillsMapper = SkillsDataMapper()
        val languagesMapper = LanguagesDataMapper()
        return ResumeData(
                value.basics?.let { basicsMapper.transform(it) },
                value.work?.let { workMapper.transformCollection(it) },
                value.skills?.let { skillsMapper.transformCollection(it) },
                value.languages?.let { languagesMapper.transformCollection(it) })
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

    inner class LanguagesDataMapper: Transform<Languages, LanguagesData>() {

        override fun transform(value: Languages): LanguagesData {
            return LanguagesData(value.language, value.fluency)
        }
    }
}