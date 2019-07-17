package be.mickverm.widget.validatingedittext.validators

import androidx.annotation.StringRes

class LengthInputValidator(
    @StringRes private val errorRes: Int,
    private val minLength: Int,
    private val maxLength: Int
) : InputValidator {

    override fun validate(input: String?) = when {
        input == null || input.length < minLength || input.length > maxLength -> errorRes
        else -> null
    }
}