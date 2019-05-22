package com.angelvargas.cvapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.angelvargas.cvapp.domain.models.BasicsData
import com.angelvargas.cvapp.domain.models.SkillsData
import com.angelvargas.cvapp.domain.models.WorkData
import com.angelvargas.cvapp.domain.usecase.GetResumeInformationUseCase
import com.angelvargas.cvapp.managers.ResumeResourceManager
import com.angelvargas.cvapp.presenter.ResumePresenter
import com.angelvargas.cvapp.view.ErrorView
import com.angelvargas.cvapp.view.LoadingView
import com.angelvargas.data.executor.JobExecutor
import com.angelvargas.data.executor.UiExecutor
import com.angelvargas.data.network.ApiServiceFactory
import com.angelvargas.data.repository.ResumeDataRepository
import com.angelvargas.data.services.ResumeApiServices

class MainActivity : AppCompatActivity(), ErrorView, LoadingView, ResumeContract.View {

    private lateinit var mainView: View
    private lateinit var progressBar: View

    private val resumePresenter by lazy {
        ResumePresenter(this,
                this,
                this,
                ResumeResourceManager(resources),
                GetResumeInformationUseCase(ResumeDataRepository(ApiServiceFactory()
                    .makeApiService(ResumeApiServices::class.java)),
                    JobExecutor(),
                    UiExecutor()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resumePresenter.initView()
    }

    override fun onResume() {
        super.onResume()
        resumePresenter.getResumeInformation()
    }

    override fun initializeViewComponents() {
        //TO-BE-DONE
    }

    override fun renderBasicInformation(basicInformation: BasicsData) {
        //TO-BE-DONE
    }

    override fun renderSkillsInformation(skillsInformation: List<SkillsData>) {
        //TO-BE-DONE
    }

    override fun renderWorksInformation(workInformation: List<WorkData>) {
        //TO-BE-DONE
    }

    override fun showBasicsPlaceHolder() {
        //TO-BE-DONE
    }

    override fun showSkillsPlaceHolder() {
        //TO-BE-DONE
    }

    override fun showWorksPlaceHolder() {
        //TO-BE-DONE
    }

    override fun showError(errorMessage: String) {
        showSnackbar(errorMessage)
    }

    override fun showProgressBar() {
//       progressBar.visibility = VISIBLE
    }

    override fun hideProgressBar() {
//        progressBar.visibility = GONE
    }

    private fun showSnackbar(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
