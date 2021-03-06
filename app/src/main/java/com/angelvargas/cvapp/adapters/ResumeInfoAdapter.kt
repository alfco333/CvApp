package com.angelvargas.cvapp.adapters

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.ViewGroup
import com.angelvargas.cvapp.adapters.delegates.SectionTitleDelegateAdapter
import com.angelvargas.cvapp.adapters.delegates.SkillsDelegateAdapter
import com.angelvargas.cvapp.adapters.delegates.WorksDelegateAdapter
import com.angelvargas.cvapp.adapters.viewtypes.*
import com.angelvargas.cvapp.domain.manager.ResourceManager
import com.angelvargas.cvapp.services.ImageService
import javax.inject.Inject

class ResumeInfoAdapter @Inject constructor(
    val imageService: ImageService,
    val resourceManager: ResourceManager
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var resumeViewTypes: MutableList<RecyclerViewType>? = null

    private val delegateAdapters =
        SparseArray<DelegateAdapter<in RecyclerView.ViewHolder, in RecyclerViewType>>(4)

    init {
        delegateAdapters.append(SKILLS_VIEW_TYPE, SkillsDelegateAdapter()
                as DelegateAdapter<in RecyclerView.ViewHolder, in RecyclerViewType>)
        delegateAdapters.append(WORK_VIEW_TYPE, WorksDelegateAdapter(imageService, resourceManager)
                as DelegateAdapter<in RecyclerView.ViewHolder, in RecyclerViewType>)
        delegateAdapters.append(SECTION_VIEW_TYPE, SectionTitleDelegateAdapter()
                as DelegateAdapter<in RecyclerView.ViewHolder, in RecyclerViewType>)
    }

    fun setData(resumeViewTypes: MutableList<RecyclerViewType>?) {
        this.resumeViewTypes = resumeViewTypes
    }

    override fun getItemViewType(position: Int): Int {
        return resumeViewTypes?.get(position)?.getViewType() ?: BASICS_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters[viewType]
            .onCreateViewHolder(parent) as RecyclerView.ViewHolder
    }

    override fun getItemCount(): Int {
        return resumeViewTypes?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = resumeViewTypes?.get(position)
        if (viewType != null) {
            val delegateAdapter = delegateAdapters[viewType.getViewType()]
            if (delegateAdapter == null) {
                throw IllegalStateException("There is no delegate for the kind of view")
            } else {
                delegateAdapter.onBindViewHolder(holder, viewType)
            }
        }
    }
}