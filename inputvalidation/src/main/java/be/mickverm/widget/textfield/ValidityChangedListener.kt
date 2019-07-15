package be.mickverm.widget.textfield

import android.widget.EditText

interface ValidityChangedListener {

    fun onValidityChanged(editText: EditText, input: String, valid: Boolean)
}