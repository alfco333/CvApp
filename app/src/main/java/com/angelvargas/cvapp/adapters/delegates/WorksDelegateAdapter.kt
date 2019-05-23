package com.angelvargas.cvapp.adapters.delegates

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.angelvargas.cvapp.R
import com.angelvargas.cvapp.adapters.DelegateAdapter
import com.angelvargas.cvapp.adapters.viewtypes.WorkViewType
import com.angelvargas.cvapp.domain.manager.ResourceManager
import com.angelvargas.cvapp.services.ImageService

class WorksDelegateAdapter(
    private val imageService: ImageService,
    private val resourceManager: ResourceManager
): DelegateAdapter<WorksDelegateAdapter.WorkViewHolder, WorkViewType> {

    override fun onCreateViewHolder(parent: ViewGroup): WorkViewHolder {
        return WorkViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.work_information_item, parent, false))
    }

    override fun onBindViewHolder(viewHolder: WorkViewHolder, viewType: WorkViewType) {
        viewHolder.bind(viewType, imageService, resourceManager)
    }

    class WorkViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val companyLogo = itemView.findViewById<ImageView>(R.id.iv_company_logo)
        private val tvPosition = itemView.findViewById<TextView>(R.id.tv_position)
        private val tvCompanyName = itemView.findViewById<TextView>(R.id.tv_company)
        private val tvWorkingPeriod = itemView.findViewById<TextView>(R.id.tv_work_period)
        private val tvInformation = itemView.findViewById<TextView>(R.id.tv_information)

        fun bind(workInformation: WorkViewType,
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