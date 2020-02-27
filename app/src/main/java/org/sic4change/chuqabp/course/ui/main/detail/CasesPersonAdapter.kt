package org.sic4change.chuqabp.course.ui.main.detail

import org.sic4change.domain.Case
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.basicDiffUtil
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.databinding.ViewCaseBinding
import org.sic4change.chuqabp.databinding.ViewCasePersonBinding


class CasesPersonAdapter(private val listener: (Case) -> Unit) :
    RecyclerView.Adapter<CasesPersonAdapter.ViewHolder>() {

    var cases: List<Case> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            parent.bindingInflate(
                R.layout.view_case_person,
                false
            )
        )

    override fun getItemCount(): Int = cases.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val case = cases[position]
        holder.dataBinding.item = case
        holder.itemView.setOnClickListener { listener(case) }
    }

    class ViewHolder(val dataBinding: ViewCasePersonBinding) : RecyclerView.ViewHolder(dataBinding.root)
}
