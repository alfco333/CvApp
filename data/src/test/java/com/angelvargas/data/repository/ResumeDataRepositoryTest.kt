package com.angelvargas.data.repository

import com.angelvargas.cvapp.domain.repository.ResumeRepository
import com.angelvargas.cvapp.domain.usecase.GetResumeInformationUseCase
import com.angelvargas.data.models.Basics
import com.angelvargas.data.models.Profiles
import com.angelvargas.data.models.Skills
import com.angelvargas.data.models.Work
import com.angelvargas.data.remote.ResumeResponse
import com.angelvargas.data.services.ResumeApiServices
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.net.SocketTimeoutException
import org.mockito.Mockito.`when` as whenever

class ResumeDataRepositoryTest {

    @Mock
    private lateinit var resumeApiServices: ResumeApiServices

    private lateinit var resumeRepository: ResumeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        resumeRepository = ResumeDataRepository(resumeApiServices)
    }

    @Test
    fun testGetInformationTimeoutError() {

        whenever(resumeApiServices.getResumeInformation()).thenReturn(Single.error(SocketTimeoutException()))

        resumeRepository.getCvInformation()
            .test()
            .assertError(GetResumeInformationUseCase.ResumeException.TimeoutException::class.java)
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
    }

    @Test
    fun testGetInformationUnexpectedException() {
        whenever(resumeApiServices.getResumeInformation()).thenReturn(Single.error(Exception()))

        resumeRepository.getCvInformation()
            .test()
            .assertError(GetResumeInformationUseCase.ResumeException.GenericError::class.java)
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
    }
}