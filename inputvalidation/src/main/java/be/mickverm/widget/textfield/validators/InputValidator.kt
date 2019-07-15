package be.mickverm.widget.textfield.validators

import androidx.annotation.StringRes

interface InputValidator {

    @StringRes
    fun validate(input: String?): Int?
}