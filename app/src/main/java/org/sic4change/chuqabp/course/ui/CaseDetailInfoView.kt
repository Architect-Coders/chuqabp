package org.sic4change.chuqabp.course.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import org.sic4change.chuqabp.course.model.Case

class CaseDetailInfoView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : TextView(context, attrs, defStyleAttr) {

    fun setCase(case: Case) = with(case) {
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
}