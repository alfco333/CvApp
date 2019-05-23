package com.angelvargas.cvapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.angelvargas.cvapp.R

class WorkSkillsAdapter(private var skillsList: MutableList<String>?)
    : RecyclerView.Adapter<WorkSkillsAdapter.SkillViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        return SkillViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_skill, parent, false))
    }

    override fun getItemCount(): Int {
        return skillsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        holder.setSKillName(skillsList?.get(position) ?: "")
    }

    fun refreshData(skillsList: MutableList<String>?) {
        skillsList?.let {
            if (this.skillsList == null) {
                this.skillsList = it
            } else {
                this.skillsList?.clear()
                this.skillsList?.addAll(it)
            }
            notifyDataSetChanged()
        }
    }

    class SkillViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val tvSkillName: TextView = view.findViewById(R.id.tv_skill)

        fun setSKillName(skillName: String) {
            tvSkillName.text = skillName
        }
    }
}