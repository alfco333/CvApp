package com.angelvargas.cvapp.adapters

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.ViewGroup
import com.angelvargas.cvapp.adapters.delegates.BasicsDelegateAdapter
import com.angelvargas.cvapp.adapters.viewtypes.BASICS_VIEW_TYPE
import com.angelvargas.cvapp.services.ImageService

class ResumeInfoAdapter(
    imageService: ImageService,
    private val basicsViewType: MutableList<RecyclerViewType>?
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val delegateAdapters =
        SparseArray<DelegateAdapter<in RecyclerView.ViewHolder, in RecyclerViewType>>(3)

    init {
        delegateAdapters.append(BASICS_VIEW_TYPE, BasicsDelegateAdapter(imageService)
                as DelegateAdapter<in RecyclerView.ViewHolder, in RecyclerViewType>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters[basicsViewType?.get(viewType)?.getViewType() ?: BASICS_VIEW_TYPE]
            .onCreateViewHolder(parent) as RecyclerView.ViewHolder
    }

    override fun getItemCount(): Int {
        return basicsViewType?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = basicsViewType?.get(position)
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