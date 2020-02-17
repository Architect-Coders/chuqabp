package org.sic4change.chuqabp.course.ui.main.main

import org.sic4change.domain.Person
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.basicDiffUtil
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.databinding.ViewPersonBinding


class PersonsAdapter(private val listener: (Person) -> Unit) :
    RecyclerView.Adapter<PersonsAdapter.ViewHolder>() {

    var persons: List<Person> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            parent.bindingInflate(
                R.layout.view_person,
                false
            )
        )

    override fun getItemCount(): Int = persons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = persons[position]
        holder.dataBinding.item = person
        holder.itemView.setOnClickListener { listener(person) }
    }

    class ViewHolder(val dataBinding: ViewPersonBinding) : RecyclerView.ViewHolder(dataBinding.root)
}
