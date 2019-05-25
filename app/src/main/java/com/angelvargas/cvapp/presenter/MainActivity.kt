package com.angelvargas.cvapp.presenter

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.angelvargas.cvapp.CvApplication
import com.angelvargas.cvapp.R
import com.angelvargas.cvapp.adapters.ResumeInfoAdapter
import com.angelvargas.cvapp.adapters.viewtypes.SectionTitleViewType
import com.angelvargas.cvapp.domain.manager.ResourceManager
import com.angelvargas.cvapp.domain.models.BasicsData
import com.angelvargas.cvapp.domain.models.SkillsData
import com.angelvargas.cvapp.domain.models.WorkData
import com.angelvargas.cvapp.domain.usecase.GetResumeInformationUseCase
import com.angelvargas.cvapp.mappers.SkillsViewTypeMapper
import com.angelvargas.cvapp.mappers.WorkViewTypeMapper
import com.angelvargas.cvapp.services.PicassImageService
import com.angelvargas.cvapp.view.ErrorView
import com.angelvargas.cvapp.view.LoadingView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ErrorView, LoadingView, ResumeContract.View {

    private var progressBar: LottieAnimationView? = null
    private var appbarContainer: AppBarLayout? = null
    private var profileImage: ImageView? = null
    private var profileDescription: TextView? = null
    private var placeholderContainer: View? = null
    private var resumeInfo: RecyclerView? = null
    private var collapsinToolBar: CollapsingToolbarLayout? = null
    private var resumeInfoAdapter: ResumeInfoAdapter? = null

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
        (application as CvApplication).appComponent.inject(this)
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
        appbarContainer = this.findViewById(R.id.appbar_container)
        profileImage = this.findViewById(R.id.iv_profile_image)
        profileDescription = this.findViewById(R.id.tv_profile_description)
        placeholderContainer = this.findViewById(R.id.container_placeholder)
        resumeInfo = this.findViewById(R.id.rv_resume_info)
        collapsinToolBar = findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
    }

    override fun renderResumeInformation(basicsData: BasicsData,
                                         skillsData: List<SkillsData>,
                                         workData: List<WorkData>) {
        resumeInfo?.visibility = VISIBLE
        appbarContainer?.visibility = VISIBLE
        placeholderContainer?.visibility = GONE
        collapsinToolBar?.title = basicsData.name

        profileDescription?.text = basicsData.summary
        profileImage?.let { imageService.loadImageInto(it, basicsData.picture) }

        resumeInfoAdapter = ResumeInfoAdapter(imageService, resourceManager)
        resumeInfoAdapter?.setData(mutableListOf(
            SectionTitleViewType(resourceManager.getSkillsSectionTitle()),
            *SkillsViewTypeMapper().transformCollection(skillsData).toTypedArray(),
            SectionTitleViewType(resourceManager.getWorkSectionTitle()),
            *WorkViewTypeMapper().transformCollection(workData).toTypedArray()))
        resumeInfo?.apply {
            adapter = resumeInfoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun showResumePlaceHolder() {
        appbarContainer?.visibility = GONE
        placeholderContainer?.visibility = VISIBLE
    }

    override fun showError(errorMessage: String) {
        showSnackbar(errorMessage)
    }

    override fun showProgressBar() {
        resumeInfo?.visibility = GONE
        progressBar?.visibility = VISIBLE
        appbarContainer?.visibility = GONE
    }

    override fun hideProgressBar() {
        progressBar?.visibility = GONE
    }

    private fun showSnackbar(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
