package com.angelvargas.data.repository

import com.angelvargas.cvapp.domain.models.*
import com.angelvargas.cvapp.domain.repository.ResumeRepository
import com.angelvargas.cvapp.domain.usecase.GetResumeInformationUseCase
import com.angelvargas.data.database.LocalDataSource
import com.angelvargas.data.models.Basics
import com.angelvargas.data.models.Profiles
import com.angelvargas.data.models.Skills
import com.angelvargas.data.models.Work
import com.angelvargas.data.remote.ResumeResponse
import com.angelvargas.data.services.ResumeApiServices
import io.reactivex.Single
import org.junit.Assert.assertEquals

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.net.SocketTimeoutException
import org.mockito.Mockito.`when` as whenever

class ResumeDataRepositoryTest {

    @Mock
    private lateinit var resumeApiServices: ResumeApiServices
    @Mock
    private lateinit var realmDatasource: LocalDataSource

    private lateinit var resumeRepository: ResumeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        resumeRepository = ResumeDataRepository(resumeApiServices, realmDatasource)
    }

    @Test
    fun testGetInformationTimeoutError() {
        whenever(resumeApiServices.getResumeInformation()).thenReturn(Single.error(SocketTimeoutException()))
        whenever(realmDatasource.getLocalCvInformation()).thenReturn(Single.just(ResumeData()))

        resumeRepository.getCvInformation()
            .test()
            .assertComplete()
    }

    @Test
    fun testGetInformationSuccessful() {
        val receivedProfiles = createProfilesList()
        val basicsResponse = Basics("name",
            "label",
            "urlImage",
            "email",
            "summary",
            receivedProfiles)
        val worksResponse = createWorkList()
        val skillsResponse = createSkillsList()
        val response = ResumeResponse(basicsResponse,
            worksResponse,
            skillsResponse)

        whenever(resumeApiServices.getResumeInformation()).thenReturn(Single.just(response))

        val receivedResponse = resumeRepository.getCvInformation().test().assertComplete().values()[0]
        val receivedBasics = receivedResponse.basics
        val receivedWork = receivedResponse.work?.get(0)
        val receivedSkill = receivedResponse.skills?.get(0)
        assertEquals(receivedBasics?.name, basicsResponse.name)
        assertEquals(receivedBasics?.label, basicsResponse.label)
        assertEquals(receivedBasics?.picture, basicsResponse.picture)
        assertEquals(receivedBasics?.email, basicsResponse.email)
        assertEquals(receivedBasics?.summary, basicsResponse.summary)
        assertEquals(receivedBasics?.profiles?.size, receivedProfiles.size)
        assertEquals(receivedWork?.company, WORK_COMPANY)
        assertEquals(receivedWork?.position, WORK_POSITION)
        assertEquals(receivedWork?.startDate, WORK_START_DATE)
        assertEquals(receivedWork?.endDate, WORK_END_DATE)
        assertEquals(receivedWork?.urlImage, WORK_URL_IMAGE)
        assertEquals(receivedWork?.summary, WORK_SUMMARY)
        assertEquals(receivedSkill?.name, SKILL_NAME)
        assertEquals(receivedSkill?.level, SKILL_LEVEL)
        assertEquals(receivedSkill?.keywords?.size, SKILL_KEYWORDS.size)

        verify(realmDatasource).storeReceivedData(response)
    }

    @Test
    fun testGetInformationUnexpectedException() {
        whenever(resumeApiServices.getResumeInformation()).thenReturn(Single.error(Exception()))
        whenever(realmDatasource.getLocalCvInformation()).thenReturn(Single.error(IllegalStateException()))

        resumeRepository.getCvInformation()
            .test()
            .assertError(GetResumeInformationUseCase.ResumeException.GenericError::class.java)

        verify(realmDatasource).getLocalCvInformation()
    }

    @Test
    fun testGetLocalInformationSuccessful() {
        val localBasicsData = createBasicsData()
        val localWorkData = createWorkDataList()
        val localSkillsData = createSkillDataList()
        val localResume = ResumeData(localBasicsData, localWorkData, localSkillsData)
        whenever(realmDatasource.getLocalCvInformation()).thenReturn(Single.just(localResume))

        resumeRepository.getLocalCvInformation()
            .test()
            .assertComplete()

        val receivedResponse = resumeRepository.getLocalCvInformation().test().assertComplete().values()[0]
        val receivedBasics = receivedResponse.basics
        val receivedWork = receivedResponse.work?.get(0)
        val receivedSkill = receivedResponse.skills?.get(0)
        assertEquals(receivedBasics?.name, localBasicsData.name)
        assertEquals(receivedBasics?.label, localBasicsData.label)
        assertEquals(receivedBasics?.picture, localBasicsData.picture)
        assertEquals(receivedBasics?.email, localBasicsData.email)
        assertEquals(receivedBasics?.summary, localBasicsData.summary)
        assertEquals(receivedBasics?.profiles?.size, localBasicsData.profiles?.size)
        assertEquals(receivedWork?.company, WORK_COMPANY)
        assertEquals(receivedWork?.position, WORK_POSITION)
        assertEquals(receivedWork?.startDate, WORK_START_DATE)
        assertEquals(receivedWork?.endDate, WORK_END_DATE)
        assertEquals(receivedWork?.urlImage, WORK_URL_IMAGE)
        assertEquals(receivedWork?.summary, WORK_SUMMARY)
        assertEquals(receivedSkill?.name, SKILL_NAME)
        assertEquals(receivedSkill?.level, SKILL_LEVEL)
        assertEquals(receivedSkill?.keywords?.size, SKILL_KEYWORDS.size)
    }

    private fun createProfilesList(): List<Profiles> {
        return listOf(Profiles(PROFILE_NETWORK, PROFILE_USER_NAME, PROFILE_URL))
    }

    private fun createWorkList(): List<Work> {
        return listOf(Work(WORK_COMPANY,
            WORK_POSITION,
            WORK_URL_IMAGE,
            WORK_START_DATE,
            WORK_END_DATE,
            WORK_SUMMARY,
            WORK_HIGHLIGHTS))
    }

    private fun createSkillsList(): List<Skills> {
        return listOf(Skills(SKILL_NAME, SKILL_LEVEL, SKILL_KEYWORDS))
    }

    private fun createSkillDataList(): List<SkillsData> {
        return listOf(SkillsData(SKILL_NAME, SKILL_LEVEL, SKILL_KEYWORDS))
    }

    private fun createWorkDataList(): List<WorkData> {
        return listOf(WorkData(WORK_COMPANY,
            WORK_POSITION,
            WORK_URL_IMAGE,
            WORK_START_DATE,
            WORK_END_DATE,
            WORK_SUMMARY,
            WORK_HIGHLIGHTS))
    }

    private fun createProfilesDataList(): List<ProfileData> {
        return listOf(ProfileData(PROFILE_NETWORK, PROFILE_USER_NAME, PROFILE_URL))
    }

    private fun createBasicsData(): BasicsData {
        return BasicsData(BASICS_NAME,
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