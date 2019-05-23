package com.angelvargas.cvapp.adapters.delegates

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.angelvargas.cvapp.R
import com.angelvargas.cvapp.adapters.DelegateAdapter
import com.angelvargas.cvapp.adapters.viewtypes.SectionTitleViewType

class SectionTitleDelegateAdapter
    : DelegateAdapter<SectionTitleDelegateAdapter.SectionTitleViewHolder, SectionTitleViewType> {

    override fun onCreateViewHolder(parent: ViewGroup): SectionTitleViewHolder {
        return SectionTitleViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.section_title_item, parent, false))
    }

    override fun onBindViewHolder(viewHolder: SectionTitleViewHolder, viewType: SectionTitleViewType) {
        viewHolder.bind(viewType)
    }

    class SectionTitleViewHolder(sectionTitleView: View): RecyclerView.ViewHolder(sectionTitleView) {

        fun bind(sectionViewType: SectionTitleViewType) {
            (itemView as TextView).text = sectionViewType.sectionTitle
        }
    }
}