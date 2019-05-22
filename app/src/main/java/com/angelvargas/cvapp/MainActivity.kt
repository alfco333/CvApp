package com.angelvargas.cvapp

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.angelvargas.cvapp.adapters.PreviousWorksAdapter
import com.angelvargas.cvapp.adapters.WorkSkillsAdapter
import com.angelvargas.cvapp.domain.manager.ResourceManager
import com.angelvargas.cvapp.domain.models.BasicsData
import com.angelvargas.cvapp.domain.models.SkillsData
import com.angelvargas.cvapp.domain.models.WorkData
import com.angelvargas.cvapp.domain.usecase.GetResumeInformationUseCase
import com.angelvargas.cvapp.managers.ResumeResourceManager
import com.angelvargas.cvapp.presenter.ResumePresenter
import com.angelvargas.cvapp.services.PicassImageService
import com.angelvargas.cvapp.view.ErrorView
import com.angelvargas.cvapp.view.LoadingView
import com.angelvargas.data.database.RealmProvider
import com.angelvargas.data.database.RealmResumeDataSource
import com.angelvargas.data.executor.JobExecutor
import com.angelvargas.data.executor.UiExecutor
import com.angelvargas.data.network.ApiServiceFactory
import com.angelvargas.data.repository.ResumeDataRepository
import com.angelvargas.data.services.ResumeApiServices

class MainActivity : AppCompatActivity(), ErrorView, LoadingView, ResumeContract.View {

    private var skillsRecyclerView: RecyclerView? = null
    private var previousWorksRecyclerView: RecyclerView? = null
    private var progressBar: LottieAnimationView? = null
    private var infoContainer: ConstraintLayout? = null
    private var profileName: TextView? = null
    private var profileImage: ImageView? = null
    private var profileDescription: TextView? = null
    private var skillsTitle: TextView? = null
    private var placeholderContainer: View? = null

    private var workSkillsAdapter: WorkSkillsAdapter? = null
    private var previousWorksAdapter: PreviousWorksAdapter? = null
    private lateinit var resourceManager: ResourceManager

    private val imageService = PicassImageService()

    private val resumePresenter by lazy {
        ResumePresenter(this,
                this,
                this,
                ResumeResourceManager(resources),
                GetResumeInformationUseCase(ResumeDataRepository(ApiServiceFactory()
                    .makeApiService(ResumeApiServices::class.java), RealmResumeDataSource(RealmProvider())),
                    JobExecutor(),
                    UiExecutor()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resourceManager = ResumeResourceManager(resources)
        resumePresenter.initView()
    }

    override fun onResume() {
        super.onResume()
        resumePresenter.getResumeInformation()
    }

    override fun onDestroy() {
        super.onDestroy()
        resumePresenter.dispose()
    }

    override fun initializeViewComponents() {
        progressBar = this.findViewById(R.id.lottie_loading)
        skillsRecyclerView = this.findViewById(R.id.skills_list)
        previousWorksRecyclerView = this.findViewById(R.id.work_experience_list)
        profileName = this.findViewById(R.id.tv_profile_name)
        profileImage = this.findViewById(R.id.iv_profile_image)
        profileDescription = this.findViewById(R.id.tv_profile_description)
        infoContainer = this.findViewById(R.id.info_container)
        skillsTitle = this.findViewById(R.id.tv_profile_main_skill)
        placeholderContainer = this.findViewById(R.id.container_placeholder)
    }

    override fun renderBasicInformation(basicInformation: BasicsData) {
        infoContainer?.visibility = VISIBLE
        placeholderContainer?.visibility = GONE
        profileName?.text = basicInformation.name
        profileDescription?.text = basicInformation.summary
        profileImage?.let { imageService.loadImageInto(it, basicInformation.picture) }
    }

    override fun renderSkillsInformation(skillsInformation: List<SkillsData>) {
        val receivedSkill = skillsInformation[0]
        skillsTitle?.text = receivedSkill.name
        if (workSkillsAdapter == null) {
            workSkillsAdapter = WorkSkillsAdapter(receivedSkill.keywords?.toMutableList())
            skillsRecyclerView?.apply {
                layoutManager = GridLayoutManager(this@MainActivity, 4, GridLayoutManager.HORIZONTAL, false)
                adapter = workSkillsAdapter
            }
        } else {
            workSkillsAdapter?.refreshData(receivedSkill.keywords?.toMutableList())
        }
    }

    override fun renderWorksInformation(workInformation: List<WorkData>) {
        if (previousWorksAdapter == null) {
            previousWorksAdapter = PreviousWorksAdapter(workInformation.toMutableList(), imageService, resourceManager)
            previousWorksRecyclerView?.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
                adapter = previousWorksAdapter
            }
        } else {
            previousWorksAdapter?.refreshData(workInformation.toMutableList())
        }
    }

    override fun showResumePlaceHolder() {
        infoContainer?.visibility = GONE
        placeholderContainer?.visibility = VISIBLE
    }

    override fun showError(errorMessage: String) {
        showSnackbar(errorMessage)
    }

    override fun showProgressBar() {
       progressBar?.visibility = VISIBLE
    }

    override fun hideProgressBar() {
        progressBar?.visibility = GONE
    }

    private fun showSnackbar(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
