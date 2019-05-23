package com.angelvargas.cvapp.presenter

import com.angelvargas.cvapp.ResumeContract
import com.angelvargas.cvapp.domain.manager.ResourceManager
import com.angelvargas.cvapp.domain.models.BasicsData
import com.angelvargas.cvapp.domain.usecase.GetResumeInformationUseCase
import com.angelvargas.cvapp.view.ErrorView
import com.angelvargas.cvapp.view.LoadingView
import io.reactivex.disposables.CompositeDisposable

class ResumePresenter(
        private val resumeView: ResumeContract.View,
        private val loadingView: LoadingView,
        private val errorView: ErrorView,
        private val resourceManager: ResourceManager,
        private val resumeUseCase: GetResumeInformationUseCase
): ResumeContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun initView() {
        resumeView.initializeViewComponents()
    }

    override fun getResumeInformation() {
            compositeDisposable.add(resumeUseCase.execute()
                    .doOnSubscribe { loadingView.showProgressBar() }
                    .subscribe ({ resumeData ->
                        loadingView.hideProgressBar()
                        if (resumeData.basics == null || resumeData.skills == null || resumeData.work == null) {
                            resumeView.showResumePlaceHolder()
                        } else {
                            resumeView.renderResumeInformation(resumeData.basics ?: BasicsData(),
                                resumeData.skills ?: emptyList(),
                                resumeData.work ?: emptyList())
                        }
                    }, {
                        loadingView.hideProgressBar()
                        resumeView.showResumePlaceHolder()
                        if (it is GetResumeInformationUseCase.ResumeException.TimeoutException) {
                            errorView.showError(resourceManager.getTimeoutExceptionMessage())
                        } else {
                            errorView.showError(resourceManager.getGenericExceptionMessage())
                        }
                    }))
    }

    override fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}