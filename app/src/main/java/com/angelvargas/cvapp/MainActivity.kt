package com.angelvargas.cvapp

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.angelvargas.cvapp.adapters.PreviousWorksAdapter
import com.angelvargas.cvapp.adapters.ResumeInfoAdapter
import com.angelvargas.cvapp.adapters.WorkSkillsAdapter
import com.angelvargas.cvapp.adapters.viewtypes.SectionTitleViewType
import com.angelvargas.cvapp.domain.manager.ResourceManager
import com.angelvargas.cvapp.domain.models.BasicsData
import com.angelvargas.cvapp.domain.models.SkillsData
import com.angelvargas.cvapp.domain.models.WorkData
import com.angelvargas.cvapp.domain.usecase.GetResumeInformationUseCase
import com.angelvargas.cvapp.mappers.SkillsViewTypeMapper
import com.angelvargas.cvapp.mappers.WorkViewTypeMapper
import com.angelvargas.cvapp.presenter.ResumePresenter
import com.angelvargas.cvapp.services.PicassImageService
import com.angelvargas.cvapp.view.ErrorView
import com.angelvargas.cvapp.view.LoadingView
import javax.inject.Inject

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
    private var resumeInfo: RecyclerView? = null
    private var collapsinToolBar: CollapsingToolbarLayout? = null

    private var workSkillsAdapter: WorkSkillsAdapter? = null
    private var previousWorksAdapter: PreviousWorksAdapter? = null
    private var resumeInfoAdapter: ResumeInfoAdapter? = null
    private lateinit var resourceManager: ResourceManager

    private val imageService = PicassImageService()

    @Inject
    lateinit var resourceManager: ResourceManager
    @Inject
    lateinit var resumeInformationUseCase: GetResumeInformationUseCase

    private val resumePresenter by lazy {
        ResumePresenter(this,
            this,
            this,
            resourceManager,
            resumeInformationUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume)
        (application as CvApplication).getAppComponent().inject(this)
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
        resumeInfo = this.findViewById(R.id.rv_resume_info)
        collapsinToolBar = findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
    }

    override fun renderResumeInformation(basicsData: BasicsData,
                                         skillsData: List<SkillsData>,
                                         workData: List<WorkData>) {
        collapsinToolBar?.title = basicsData.name

        profileDescription?.text = basicsData.summary
        profileImage?.let { imageService.loadImageInto(it, basicsData.picture) }

        resumeInfoAdapter = ResumeInfoAdapter(imageService,
            resourceManager,
            mutableListOf(SectionTitleViewType(resourceManager.getSkillsSectionTitle()),
                *SkillsViewTypeMapper().transformCollection(skillsData).toTypedArray(),
                SectionTitleViewType(resourceManager.getWorkSectionTitle()),
                *WorkViewTypeMapper().transformCollection(workData).toTypedArray()))
        resumeInfo?.apply {
            adapter = resumeInfoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun showResumePlaceHolder() {
//        infoContainer?.visibility = GONE
//        placeholderContainer?.visibility = VISIBLE
    }

    override fun showError(errorMessage: String) {
        showSnackbar(errorMessage)
    }

    override fun showProgressBar() {
//       progressBar?.visibility = VISIBLE
    }

    override fun hideProgressBar() {
//        progressBar?.visibility = GONE
    }

    private fun showSnackbar(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
