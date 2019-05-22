package com.angelvargas.cvapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.angelvargas.cvapp.R
import com.angelvargas.cvapp.domain.manager.ResourceManager
import com.angelvargas.cvapp.domain.models.WorkData
import com.angelvargas.cvapp.services.ImageService

class PreviousWorksAdapter(private var previousWorksList: MutableList<WorkData>?,
                           private val imageService: ImageService,
                           private val resourceManager: ResourceManager)
    : RecyclerView.Adapter<PreviousWorksAdapter.PreviousWorkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviousWorkViewHolder {
        return PreviousWorkViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_work_information, parent, false))
    }

    override fun getItemCount(): Int {
        return previousWorksList?.size ?: 0
    }

    override fun onBindViewHolder(holder: PreviousWorkViewHolder, position: Int) {
        previousWorksList?.let { holder.setWorkInformation(it[position], imageService, resourceManager) }
    }

    fun refreshData(newWorksList: MutableList<WorkData>?) {
        newWorksList?.let {
            if (this.previousWorksList == null) {
                previousWorksList = it
            } else {
                previousWorksList?.clear()
                previousWorksList?.addAll(it)
            }
            notifyDataSetChanged()
        }
    }

    class PreviousWorkViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val companyLogo = view.findViewById<ImageView>(R.id.iv_company_logo)
        private val tvPosition = view.findViewById<TextView>(R.id.tv_position)
        private val tvCompanyName = view.findViewById<TextView>(R.id.tv_company)
        private val tvWorkingPeriod = view.findViewById<TextView>(R.id.tv_work_period)
        private val tvInformation = view.findViewById<TextView>(R.id.tv_information)

        fun setWorkInformation(workInformation: WorkData,
                               imageService: ImageService,
                               resourceManager: ResourceManager) {
            workInformation.run {
                imageService.loadImageInto(companyLogo, urlImage)
                tvPosition.text = position
                tvCompanyName.text = company
                tvWorkingPeriod.text = resourceManager.getWorkPeriodFormat(startDate, endDate)
                tvInformation.text = summary
            }
        }
    }
}