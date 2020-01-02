package org.sic4change.chuqabp.course.ui.main.main

import org.sic4change.domain.Case
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.basicDiffUtil
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.databinding.ViewCaseBinding


class CasesAdapter(private val listener: (Case) -> Unit) :
    RecyclerView.Adapter<CasesAdapter.ViewHolder>() {

    var cases: List<Case> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            parent.bindingInflate(
                R.layout.view_case,
                false
            )
        )

    override fun getItemCount(): Int = cases.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val case = cases[position]
        holder.dataBinding.item = case
        holder.itemView.setOnClickListener { listener(case) }
    }

    class ViewHolder(val dataBinding: ViewCaseBinding) : RecyclerView.ViewHolder(dataBinding.root)
}
