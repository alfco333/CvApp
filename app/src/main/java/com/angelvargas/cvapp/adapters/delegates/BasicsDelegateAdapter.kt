package com.angelvargas.cvapp.adapters.delegates

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.angelvargas.cvapp.R
import com.angelvargas.cvapp.adapters.DelegateAdapter
import com.angelvargas.cvapp.adapters.viewtypes.BasicsViewType
import com.angelvargas.cvapp.services.ImageService

class BasicsDelegateAdapter(private val imageService: ImageService)
    : DelegateAdapter<BasicsDelegateAdapter.BasicsViewHolder, BasicsViewType> {

    override fun onCreateViewHolder(parent: ViewGroup): BasicsViewHolder {
        return BasicsViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.basics_information_item, parent, false))
    }

    override fun onBindViewHolder(viewHolder: BasicsViewHolder, viewType: BasicsViewType) {
        viewHolder.bindData(viewType, imageService)
    }

    class BasicsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val profileName = itemView.findViewById<TextView>(R.id.tv_profile_name)
        private val profileImage = itemView.findViewById<ImageView>(R.id.iv_profile_image)
        private val profileDescription = itemView.findViewById<TextView>(R.id.tv_profile_description)

        fun bindData(basicsViewType: BasicsViewType, imageService: ImageService) {
            profileName?.text = basicsViewType.name
            profileDescription?.text = basicsViewType.summary
            profileImage?.let { imageService.loadImageInto(it, basicsViewType.picture) }
        }
    }
}