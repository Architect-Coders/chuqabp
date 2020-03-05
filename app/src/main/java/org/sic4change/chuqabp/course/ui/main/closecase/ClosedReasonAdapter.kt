package org.sic4change.chuqabp.course.ui.main.closecase

import org.sic4change.domain.ClosedReason
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.basicDiffUtil
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.databinding.ViewCloseReasonBinding


class ClosedReasonAdapter(private val listener: (ClosedReason) -> Unit) :
    RecyclerView.Adapter<ClosedReasonAdapter.ViewHolder>() {

    var closedReasons: List<ClosedReason> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            parent.bindingInflate(
                R.layout.view_close_reason,
                false
            )
        )

    override fun getItemCount(): Int = closedReasons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val closedReason = closedReasons[position]
        holder.dataBinding.closedReason = closedReason
        holder.itemView.setOnClickListener { listener(closedReason) }
    }

    class ViewHolder(val dataBinding: ViewCloseReasonBinding) : RecyclerView.ViewHolder(dataBinding.root)
}
