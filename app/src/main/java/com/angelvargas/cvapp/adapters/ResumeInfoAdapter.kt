package com.angelvargas.cvapp.adapters

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.ViewGroup
import com.angelvargas.cvapp.adapters.delegates.BasicsDelegateAdapter
import com.angelvargas.cvapp.adapters.delegates.SkillsDelegateAdapter
import com.angelvargas.cvapp.adapters.viewtypes.BASICS_VIEW_TYPE
import com.angelvargas.cvapp.adapters.viewtypes.SKILLS_VIEW_TYPE
import com.angelvargas.cvapp.services.ImageService

class ResumeInfoAdapter(
    imageService: ImageService,
    private val resumeViewTypes: MutableList<RecyclerViewType>?
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val delegateAdapters =
        SparseArray<DelegateAdapter<in RecyclerView.ViewHolder, in RecyclerViewType>>(3)

    init {
        delegateAdapters.append(BASICS_VIEW_TYPE, BasicsDelegateAdapter(imageService)
                as DelegateAdapter<in RecyclerView.ViewHolder, in RecyclerViewType>)
        delegateAdapters.append(SKILLS_VIEW_TYPE, SkillsDelegateAdapter()
                as DelegateAdapter<in RecyclerView.ViewHolder, in RecyclerViewType>)
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