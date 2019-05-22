package com.angelvargas.cvapp.domain.usecase

import com.angelvargas.cvapp.domain.ImmediateThreadExecutor
import com.angelvargas.cvapp.domain.executor.PostExecutionThread
import com.angelvargas.cvapp.domain.models.*
import com.angelvargas.cvapp.domain.repository.ResumeRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

class GetResumeInformationUseCaseTest {

    @Mock
    private lateinit var resumeRepository: ResumeRepository
    @Mock
    private lateinit var postExecutor: PostExecutionThread

    private lateinit var resumeUseCase: GetResumeInformationUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(postExecutor.getScheduler()).thenReturn(Schedulers.trampoline())
        resumeUseCase = GetResumeInformationUseCase(resumeRepository, ImmediateThreadExecutor(), postExecutor)
    }

    @Test
    fun testGetResumeInformationSuccessful() {
        val receivedProfiles = createProfilesList()
        val basicsResponse = BasicsData("name",
            "label",
            "urlImage",
            "email",
            "summary",
            receivedProfiles)
        val worksResponse = createWorkList()
        val skillsResponse = createSkillsList()
        val resumeInfo = ResumeData(basicsResponse,
            worksResponse,
            skillsResponse)

        whenever(resumeRepository.getCvInformation()).thenReturn(Single.just(resumeInfo))

        val receivedResponse = resumeRepository.getCvInformation().test().assertComplete().values()[0]

        val receivedBasics = receivedResponse.basics
        val receivedWork = receivedResponse.work?.get(0)
        val receivedSkill = receivedResponse.skills?.get(0)
        Assert.assertEquals(receivedBasics?.name, basicsResponse.name)
        Assert.assertEquals(receivedBasics?.label, basicsResponse.label)
        Assert.assertEquals(receivedBasics?.picture, basicsResponse.picture)
        Assert.assertEquals(receivedBasics?.email, basicsResponse.email)
        Assert.assertEquals(receivedBasics?.summary, basicsResponse.summary)
        Assert.assertEquals(receivedBasics?.profiles?.size, receivedProfiles.size)
        Assert.assertEquals(receivedWork?.company, WORK_COMPANY)
        Assert.assertEquals(receivedWork?.position, WORK_POSITION)
        Assert.assertEquals(receivedWork?.startDate, WORK_START_DATE)
        Assert.assertEquals(receivedWork?.endDate, WORK_END_DATE)
        Assert.assertEquals(receivedWork?.urlImage, WORK_URL_IMAGE)
        Assert.assertEquals(receivedWork?.summary, WORK_SUMMARY)
        Assert.assertEquals(receivedSkill?.name, SKILL_NAME)
        Assert.assertEquals(receivedSkill?.level, SKILL_LEVEL)
        Assert.assertEquals(receivedSkill?.keywords?.size, SKILL_KEYWORDS.size)
    }

    @Test
    fun testGetInformationTimeoutError() {
        whenever(resumeRepository.getCvInformation())
            .thenReturn(Single.error(GetResumeInformationUseCase.ResumeException.GenericError()))

        resumeRepository.getCvInformation()
            .test()
            .assertError(GetResumeInformationUseCase.ResumeException.GenericError::class.java)
            .assertErrorMessage(null)
    }


    @Test
    fun testGetInformationUnexpectedException() {
        whenever(resumeRepository.getCvInformation())
            .thenReturn(Single.error(GetResumeInformationUseCase.ResumeException.TimeoutException()))

        resumeRepository.getCvInformation()
            .test()
            .assertError(GetResumeInformationUseCase.ResumeException.TimeoutException::class.java)
            .assertErrorMessage(null)
    }


    private fun createProfilesList(): List<ProfileData> {
        return listOf(ProfileData(PROFILE_NETWORK, PROFILE_USER_NAME, PROFILE_URL))
    }

    private fun createWorkList(): List<WorkData> {
        return listOf(WorkData(WORK_COMPANY,
            WORK_POSITION,
            WORK_URL_IMAGE,
            WORK_START_DATE,
            WORK_END_DATE,
            WORK_SUMMARY,
            WORK_HIGHLIGHTS))
    }

    private fun createSkillsList(): List<SkillsData> {
        return listOf(SkillsData(SKILL_NAME, SKILL_LEVEL, SKILL_KEYWORDS))
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