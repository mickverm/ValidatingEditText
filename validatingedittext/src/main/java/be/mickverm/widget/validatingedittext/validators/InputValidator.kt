package be.mickverm.widget.validatingedittext.validators

import androidx.annotation.StringRes

interface InputValidator {

    @StringRes
    fun validate(input: String?): Int?
}