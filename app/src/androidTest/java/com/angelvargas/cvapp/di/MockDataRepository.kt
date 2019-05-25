package com.angelvargas.cvapp.di

import com.angelvargas.cvapp.domain.models.*
import com.angelvargas.cvapp.domain.repository.ResumeRepository
import io.reactivex.Single

class MockDataRepository: ResumeRepository {

    override fun getCvInformation(): Single<ResumeData> {
        return Single.just(ResumeData(createBasicsData(), createWorkDataList(), createSkillDataList()))
    }

    override fun getLocalCvInformation(): Single<ResumeData> {
        return Single.just(ResumeData())
    }

    private fun createSkillDataList(): List<SkillsData> {
        return listOf(SkillsData(
            SKILL_NAME,
            SKILL_LEVEL,
            SKILL_KEYWORDS
        ))
    }

    private fun createWorkDataList(): List<WorkData> {
        return listOf(
            WorkData(
                WORK_COMPANY,
                WORK_POSITION,
                WORK_URL_IMAGE,
                WORK_START_DATE,
                WORK_END_DATE,
                WORK_SUMMARY,
                WORK_HIGHLIGHTS
            ), WorkData(
                WORK_COMPANY,
                WORK_POSITION,
                WORK_URL_IMAGE,
                WORK_START_DATE,
                WORK_END_DATE,
                WORK_SUMMARY,
                WORK_HIGHLIGHTS
            ),
            WorkData(
                WORK_COMPANY,
                WORK_POSITION,
                WORK_URL_IMAGE,
                WORK_START_DATE,
                WORK_END_DATE,
                WORK_SUMMARY,
                WORK_HIGHLIGHTS
            ),
            WorkData(
                WORK_COMPANY,
                WORK_POSITION,
                WORK_URL_IMAGE,
                WORK_START_DATE,
                WORK_END_DATE,
                WORK_SUMMARY,
                WORK_HIGHLIGHTS
            )
        )
    }

    private fun createProfilesDataList(): List<ProfileData> {
        return listOf(ProfileData(
            PROFILE_NETWORK,
            PROFILE_USER_NAME,
            PROFILE_URL
        ))
    }

    private fun createBasicsData(): BasicsData {
        return BasicsData(
            BASICS_NAME,
            BASICS_LABEL,
            BASICS_PICTURE,
            BASICS_EMAIL,
            BASICS_SUMMARY,
            createProfilesDataList())
    }

    companion object {
        const val PROFILE_NETWORK = "profileNetwork"
        const val PROFILE_USER_NAME = "profileUserName"
        const val PROFILE_URL = "profileUrl"
        const val WORK_COMPANY = "workCompany"
        const val WORK_POSITION = "workPosition"
        const val WORK_URL_IMAGE = "workUrlImage"
        const val WORK_START_DATE = "workStartDate"
        const val WORK_END_DATE = "workEndDate"
        const val WORK_SUMMARY = "workSummary"
        val WORK_HIGHLIGHTS = listOf("highlight")
        const val SKILL_NAME = "skillName"
        const val SKILL_LEVEL = "skillLevel"
        val SKILL_KEYWORDS = listOf("android", "kotlin")
        const val BASICS_NAME = "name"
        const val BASICS_LABEL = "label"
        const val BASICS_PICTURE = "picture"
        const val BASICS_EMAIL = "email"
        const val BASICS_SUMMARY = "summary"
    }
}