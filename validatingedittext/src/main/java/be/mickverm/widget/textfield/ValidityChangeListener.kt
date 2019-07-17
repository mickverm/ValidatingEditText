package be.mickverm.widget.textfield

import android.widget.EditText

interface ValidityChangeListener {

    fun onValidityChanged(editText: EditText, input: String, valid: Boolean)
}