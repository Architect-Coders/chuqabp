package org.sic4change.chuqabp.course.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sic4change.chuqabp.course.model.database.Case
import org.sic4change.chuqabp.course.ui.common.loadUrl
import org.sic4change.chuqabp.course.ui.main.main.CasesAdapter

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadUrl(url)
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = visible?.let {
        if (visible) View.VISIBLE else View.GONE
    } ?: View.GONE
}

@BindingAdapter("case")
fun TextView.updateCaseDetails(case: Case?) = case?.run {
    text = buildSpannedString {
        bold { append("Nombre: ") }
        appendln(name)

        bold { append("Apellidos: ") }
        appendln(surnames)

        bold { append("Email: ") }
        appendln(email)

        bold { append("Teléfono: ") }
        appendln(phone)

        bold { append("Fecha de nacimiento: ") }
        append(birthdate)
    }
}

@BindingAdapter("fullname")
fun TextView.setFullNameText(case: Case?) = case?.run {
    text = buildSpannedString {
        append(name)
        append(surnames)
    }
}

@BindingAdapter("items")
fun RecyclerView.setItems(cases: List<Case>?) {
    (adapter as? CasesAdapter)?.let {
        it.cases = cases ?: emptyList()
    }
}