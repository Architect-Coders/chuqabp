package org.sic4change.chuqabp.course.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.loadPersonUrl
import org.sic4change.chuqabp.course.ui.main.cases.CasesAdapter
import org.sic4change.chuqabp.course.ui.main.closecase.ClosedReasonAdapter
import org.sic4change.chuqabp.course.ui.main.detail.CasesPersonAdapter
import org.sic4change.chuqabp.course.ui.main.main.PersonsAdapter
import org.sic4change.chuqabp.course.ui.main.newcase.ResourcesAdapter
import org.sic4change.domain.Person
import org.sic4change.domain.Case
import org.sic4change.domain.ClosedReason
import org.sic4change.domain.Resource

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadPersonUrl(url)
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = visible?.let {
        if (visible) View.VISIBLE else View.GONE
    } ?: View.GONE
}

@BindingAdapter("person")
fun TextView.updateCaseDetails(person: Person?) = person?.run {
    text = buildSpannedString {
        bold { append("Nombre: ") }
        appendln(name)

        bold { append("Apellidos: ") }
        appendln(surnames)

        bold { append("Ubicación: ") }
        appendln(location)

        bold { append("Email: ") }
        appendln(email)

        bold { append("Teléfono: ") }
        appendln(phone)

        bold { append("Fecha de nacimiento: ") }
        append(birthdate)
    }
}

@BindingAdapter("fullname")
fun TextView.setFullNameText(person: Person?) = person?.run {
    text = buildSpannedString {
        append(name)
        append(" ")
        append(surnames)
    }
}

@BindingAdapter("fullnameCase")
fun TextView.setFullNameCaseText(case: Case?) = case?.run {
    text = buildSpannedString {
        append(name)
        append(" ")
        append(surnames)
    }
}

@BindingAdapter("fullnameResource")
fun TextView.setFullNameCaseText(resource: Resource?) = resource?.run {
    text = buildSpannedString {
        append(name)
    }
}

@BindingAdapter("items")
fun RecyclerView.setItems(persons: List<Person>?) {
    (adapter as? PersonsAdapter)?.let {
        it.persons = persons ?: emptyList()
    }
}

@BindingAdapter("cases")
fun RecyclerView.setCases(cases: List<Case>?) {
    (adapter as? CasesAdapter)?.let {
        it.cases = cases ?: emptyList()
    }
}

@BindingAdapter("casesPerson")
fun RecyclerView.setCasesPerson(cases: List<Case>?) {
    (adapter as? CasesPersonAdapter)?.let {
        it.cases = cases ?: emptyList()
    }
}

@BindingAdapter("resources")
fun RecyclerView.setResources(resources: List<Resource>?) {
    (adapter as? ResourcesAdapter)?.let {
        it.resources = resources ?: emptyList()
    }
}

@BindingAdapter("resourceSelected")
fun TextView.resourceSelected(resource: Resource?) = resource?.run {
    if (resource.selected) {
        setBackgroundColor(resources.getColor(R.color.colorPrimary))
    } else {
        setBackgroundColor(resources.getColor(R.color.gray))
    }
}

@BindingAdapter("resourcesCase")
fun RecyclerView.setResourcesCase(resources: List<Resource>?) {
    (adapter as? ResourcesAdapter)?.let {
        it.resources = resources ?: emptyList()
    }
}

@BindingAdapter("closed")
fun View.setClosed(closed: String?) {
    visibility = closed?.let {
        if (closed == "closed") View.VISIBLE else View.GONE
    } ?: View.GONE
}

@BindingAdapter("open")
fun View.setOpen(open: String?) {
    visibility = open?.let {
        if (open == "open") View.VISIBLE else View.GONE
    } ?: View.GONE
}

@BindingAdapter("closedReasonSelected")
fun TextView.closedReasonSelected(closedReason: ClosedReason?) = closedReason?.run {
    if (closedReason.selected) {
        setBackgroundColor(resources.getColor(R.color.red))
    } else {
        setBackgroundColor(resources.getColor(R.color.gray))
    }
}

@BindingAdapter("closeReasons")
fun RecyclerView.setCloseReasons(closeReasons: List<ClosedReason>?) {
    (adapter as? ClosedReasonAdapter)?.let {
        it.closedReasons = closeReasons ?: emptyList()
    }
}

@BindingAdapter("selected")
fun Chip.setSelected(selected: Boolean) {
    if (selected) {
        setTextColor(resources.getColor(R.color.colorPrimaryDark))
    } else {
        setTextColor(resources.getColor(R.color.gray))
    }
}