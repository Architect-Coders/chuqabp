package org.sic4change.chuqabp.course.ui.main.newcase

import org.sic4change.domain.Resource
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.basicDiffUtil
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.databinding.ViewResourceBinding


class ResourcesAdapter(private val listener: (Resource) -> Unit) :
    RecyclerView.Adapter<ResourcesAdapter.ViewHolder>() {

    var resources: List<Resource> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            parent.bindingInflate(
                R.layout.view_resource,
                false
            )
        )

    override fun getItemCount(): Int = resources.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resource = resources[position]
        holder.dataBinding.resource = resource
        holder.itemView.setOnClickListener { listener(resource) }
    }

    class ViewHolder(val dataBinding: ViewResourceBinding) : RecyclerView.ViewHolder(dataBinding.root)
}
