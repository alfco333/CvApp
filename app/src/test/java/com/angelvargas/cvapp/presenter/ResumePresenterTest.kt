package com.angelvargas.cvapp.presenter

import com.angelvargas.cvapp.ResumeContract
import com.angelvargas.cvapp.domain.manager.ResourceManager
import com.angelvargas.cvapp.domain.models.ResumeData
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
}