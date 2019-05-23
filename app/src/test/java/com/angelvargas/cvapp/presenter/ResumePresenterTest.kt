package com.angelvargas.cvapp.presenter

import com.angelvargas.cvapp.ResumeContract
import com.angelvargas.cvapp.domain.manager.ResourceManager
import com.angelvargas.cvapp.domain.models.*
import com.angelvargas.cvapp.domain.usecase.GetResumeInformationUseCase
import com.angelvargas.cvapp.view.ErrorView
import com.angelvargas.cvapp.view.LoadingView
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

class ResumePresenterTest {

    @Mock
    private lateinit var resumeView: ResumeContract.View
    @Mock
    private lateinit var errorView: ErrorView
    @Mock
    private lateinit var loadingView: LoadingView
    @Mock
    private lateinit var resourceManager: ResourceManager
    @Mock
    private lateinit var resumeUseCase: GetResumeInformationUseCase

    private lateinit var resumePresenter: ResumeContract.Presenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        resumePresenter = ResumePresenter(resumeView, loadingView, errorView, resourceManager, resumeUseCase)
    }

    @Test
    fun testInitView() {
        resumePresenter.initView()
        verify(resumeView).initializeViewComponents()
    }

    @Test
    fun testGetResumeInformationSuccessfulWithNoData() {
        val resumeData = ResumeData()
        whenever(resumeUseCase.execute()).thenReturn(Single.just(resumeData))

        resumePresenter.getResumeInformation()

        verify(loadingView).showProgressBar()
        verify(loadingView).hideProgressBar()
        verify(resumeView, atLeastOnce()).showResumePlaceHolder()
    }

    @Test
    fun testGetResumeInformationWithOnlySkills() {
        val receivedSkillsData = createSkillDataList()
        val resumeData = ResumeData(null, null, receivedSkillsData)
        whenever(resumeUseCase.execute()).thenReturn(Single.just(resumeData))

        resumePresenter.getResumeInformation()

        verify(resumeView, atLeastOnce()).showResumePlaceHolder()
        verify(loadingView).showProgressBar()
        verify(loadingView).hideProgressBar()
        verify(resumeView).renderSkillsInformation(receivedSkillsData)
    }

    @Test
    fun testGetResumeInformationWithOnlyBasics() {
        val receivedBasicsData = createBasicsData()
        val resumeData = ResumeData(receivedBasicsData, null, null)
        whenever(resumeUseCase.execute()).thenReturn(Single.just(resumeData))

        resumePresenter.getResumeInformation()

        verify(resumeView, atLeastOnce()).showResumePlaceHolder()
        verify(loadingView).showProgressBar()
        verify(loadingView).hideProgressBar()
        verify(resumeView).renderBasicInformation(receivedBasicsData)
    }

    @Test
    fun testGetResumeInformationWithOnlyWorks() {
        val receivedWorks = createWorkDataList()
        val resumeData = ResumeData(null, receivedWorks, null)
        whenever(resumeUseCase.execute()).thenReturn(Single.just(resumeData))

        resumePresenter.getResumeInformation()

        verify(resumeView, atLeastOnce()).showResumePlaceHolder()
        verify(loadingView).showProgressBar()
        verify(loadingView).hideProgressBar()
        verify(resumeView).renderWorksInformation(receivedWorks)
    }

    @Test
    fun testGetResumeInformationWithTimeoutException() {
        val timeoutErrorMessage = "Timeout Error"
        whenever(resumeUseCase.execute()).thenReturn(Single.error(GetResumeInformationUseCase
            .ResumeException
            .TimeoutException()))
        whenever(resourceManager.getTimeoutExceptionMessage()).thenReturn(timeoutErrorMessage)

        resumePresenter.getResumeInformation()

        verify(resumeView).showResumePlaceHolder()
        verify(loadingView).showProgressBar()
        verify(loadingView).hideProgressBar()
        verify(errorView).showError(timeoutErrorMessage)
    }

    @Test
    fun testGetResumeInformationWithGenericException() {
        val genericErrorMessage = "genericMessage"
        whenever(resumeUseCase.execute()).thenReturn(Single.error(GetResumeInformationUseCase
            .ResumeException
            .GenericError()))
        whenever(resourceManager.getGenericExceptionMessage()).thenReturn(genericErrorMessage)

        resumePresenter.getResumeInformation()

        verify(resumeView).showResumePlaceHolder()
        verify(loadingView).showProgressBar()
        verify(loadingView).hideProgressBar()
        verify(errorView).showError(genericErrorMessage)
    }

    @Test
    fun testGetResumeInformationWithAnyOtherException() {
        val genericErrorMessage = "genericMessage"
        whenever(resumeUseCase.execute()).thenReturn(Single.error(IllegalStateException()))
        whenever(resourceManager.getGenericExceptionMessage()).thenReturn(genericErrorMessage)

        resumePresenter.getResumeInformation()

        verify(resumeView).showResumePlaceHolder()
        verify(loadingView).showProgressBar()
        verify(loadingView).hideProgressBar()
        verify(errorView).showError(genericErrorMessage)
    }

    private fun createSkillDataList(): List<SkillsData> {
        return listOf(SkillsData(SKILL_NAME, SKILL_LEVEL, SKILL_KEYWORDS))
    }

    private fun createWorkDataList(): List<WorkData> {
        return listOf(
            WorkData(WORK_COMPANY,
                WORK_POSITION,
                WORK_URL_IMAGE,
                WORK_START_DATE,
                WORK_END_DATE,
                WORK_SUMMARY,
                WORK_HIGHLIGHTS)
        )
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