package org.sic4change.chuqabp.course.ui

import org.sic4change.chuqabp.course.model.Case
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_case.view.*
import org.sic4change.chuqabp.R


class CasesAdapter(private val listener: (Case) -> Unit) :
    RecyclerView.Adapter<CasesAdapter.ViewHolder>() {

    var cases: List<Case> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_case, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = cases.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val case = cases[position]
        holder.bind(case)
        holder.itemView.setOnClickListener { listener(case) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(case: Case) {
            itemView.caseTitle.text = "${case.name} ${case.surnames}"
            itemView.caseCover.loadUrl(case.photo)
        }
    }
}
