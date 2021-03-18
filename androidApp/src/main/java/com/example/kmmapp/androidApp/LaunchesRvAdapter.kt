package com.example.kmmapp.androidApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kmmapp.androidApp.databinding.ItemLaunchBinding
import com.example.kmmapp.shared.entity.RocketLaunch

class LaunchesRvAdapter (var launches: List<RocketLaunch>) : RecyclerView.Adapter<LaunchesRvAdapter.LaunchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_launch, parent, false)
            .run(::LaunchViewHolder)
    }

    override fun getItemCount(): Int = launches.count()

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bindData(launches[position])
    }

    inner class LaunchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val  itemLaunch: ItemLaunchBinding = ItemLaunchBinding.bind(itemView)

        fun bindData(launch: RocketLaunch) {
            val ctx = itemView.context
            itemLaunch.apply {
                missionName.text = ctx.getString(R.string.mission_name_field, launch.missionName)
                launchYear.text = ctx.getString(R.string.launch_year_field, launch.launchYear.toString())
                details.text = ctx.getString(R.string.details_field, launch.details.orEmpty() )
                val launchSuccess = launch.launchSuccess
                if (launchSuccess != null ) {
                    if (launchSuccess) {
                        launchSuccessTxt.text = ctx.getString(R.string.successful)
                        launchSuccessTxt.setTextColor((ContextCompat.getColor(itemView.context, R.color.colorSuccessful)))
                    } else {
                        launchSuccessTxt.text = ctx.getString(R.string.unsuccessful)
                        launchSuccessTxt.setTextColor((ContextCompat.getColor(itemView.context, R.color.colorUnsuccessful)))
                    }
                } else {
                    launchSuccessTxt.text = ctx.getString(R.string.no_data)
                    launchSuccessTxt.setTextColor((ContextCompat.getColor(itemView.context, R.color.colorNoData)))
                }
            }

        }
    }
}