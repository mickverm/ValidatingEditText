package be.mickverm.widget.validatingedittext

import android.widget.EditText

interface ValidityChangeListener {

    fun onValidityChanged(editText: EditText, input: String, valid: Boolean)
}