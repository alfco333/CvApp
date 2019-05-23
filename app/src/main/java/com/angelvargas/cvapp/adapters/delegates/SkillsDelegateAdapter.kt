package com.angelvargas.cvapp.adapters.delegates

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.angelvargas.cvapp.R
import com.angelvargas.cvapp.adapters.DelegateAdapter
import com.angelvargas.cvapp.adapters.WorkSkillsAdapter
import com.angelvargas.cvapp.adapters.viewtypes.SkillsViewType

class SkillsDelegateAdapter: DelegateAdapter<SkillsDelegateAdapter.SkillsViewHolder, SkillsViewType> {

    override fun onCreateViewHolder(parent: ViewGroup): SkillsViewHolder {
        return SkillsViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.skills_information_item, parent, false))
    }

    override fun onBindViewHolder(viewHolder: SkillsViewHolder, viewType: SkillsViewType) {
        viewHolder.bind(viewType)
    }

    class SkillsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val skillsRecyclerView = itemView.findViewById<RecyclerView>(R.id.skills_list)
        private val skillsTitle = itemView.findViewById<TextView>(R.id.tv_profile_main_skill)
        private var workSkillsAdapter: WorkSkillsAdapter? = null

        fun bind(skillsViewType: SkillsViewType) {

            skillsTitle?.text = skillsViewType.name
            if (workSkillsAdapter == null) {
                workSkillsAdapter = WorkSkillsAdapter(skillsViewType.keywords?.toMutableList())
                skillsRecyclerView?.apply {
                    layoutManager = GridLayoutManager(itemView.context, 4, GridLayoutManager.HORIZONTAL, false)
                    adapter = workSkillsAdapter
                }
            } else {
                workSkillsAdapter?.refreshData(skillsViewType.keywords?.toMutableList())
            }
        }
    }
}