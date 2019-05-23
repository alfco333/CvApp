package com.angelvargas.cvapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.angelvargas.cvapp.adapters.viewtypes.RecyclerViewType

interface DelegateAdapter<VH : RecyclerView.ViewHolder, VT : RecyclerViewType> {

    fun onCreateViewHolder(parent: ViewGroup): VH

    fun onBindViewHolder(viewHolder: VH, viewType: VT)
}